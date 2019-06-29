/*
 * Copyright (c) 2012, 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
    SOURCE:
    https://github.com/netroby/jdk9-dev/blob/master/jdk/src/java.base/share/classes/java/util/Errional.java

    LORIKEET CHANGES:
    repackaged from java.util. to lorikeet
    renamed class from Optional to Err
    deprecated get for orPanic
    deprecated ifPresent for then
    added Err::of(Optional)
 */

package lorikeet;

import lorikeet.error.LorikeetException;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A lobe object which may or may not contain a non-{@code null} value.
 * If a value is present, {@code isPresent()} returns {@code true} and
 * {@code get()} returns the value.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #orElse(java.lang.Object) orElse()}
 * (returns a default value if no value is present) and
 * {@link #ifPresent(java.util.function.Consumer) ifPresent()} (performs an
 * action if a value is present).
 *
 * <p>This is a <a href="../lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code Err} may have unpredictable results and should be avoided.
 *
 * @param <T> the type of value
 * @since 1.8
 */
public final class Err<T> implements May<T> {
    /**
     * Common instance for {@code empty()}.
     */
    private static final Err<?> EMPTY = new Err<>();

    /**
     * If non-null, the value; if null, indicates no value is present
     */
    private final T value;

    /**
     *  If value is null then this exception is always present to represent the error
     */
    private final Exception exception;

    /**
     * Constructs an empty instance.
     *
     * @implNote Generally only one empty instance, {@link Err#EMPTY},
     * should exist per VM.
     */
    private Err() {
        this.value = null;
        this.exception = new NoValueException();
    }

    private Err(Exception exception) {
        this.value = null;
        this.exception = exception;
    }

    /**
     * Returns an empty {@code Err} instance.  No value is present for this
     * {@code Err}.
     *
     * @apiNote
     * Though it may be tempting to do so, avoid articletesting if an object is empty
     * by comparing with {@code ==} against instances returned by
     * {@code Err.empty()}.  There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     *
     * @param <T> The type of the non-existent value
     * @return an empty {@code Err}
     */
    public static<T> Err<T> failure() {
        @SuppressWarnings("unchecked")
        Err<T> t = (Err<T>) EMPTY;
        return t;
    }

    public static<T> Err<T> failure(Exception e) {
        return new Err<>(e);
    }

    public static <A,B,C> Err<C> join(Err<A> errA, Err<B> errB, Fun2<A,B,C> join) {
        if (!errA.isPresent()) {
            return Err.failure(errA.exception);
        }

        if (!errB.isPresent()) {
            return Err.failure(errB.exception);
        }

        return Err.of(join.apply(errA.orPanic(), errB.orPanic()));
    }

    /**
     * Constructs an instance with the described value.
     *
     * @param value the non-{@code null} value to describe
     * @throws NullPointerException if value is {@code null}
     */
    private Err(T value) {
        this.value = Objects.requireNonNull(value);
        this.exception = null;
    }

    /**
     * Returns an {@code Err} describing the given non-{@code null}
     * value.
     *
     * @param value the value to describe, which must be non-{@code null}
     * @param <T> the type of the value
     * @return an {@code Err} with the value present
     * @throws NullPointerException if value is {@code null}
     */
    public static <T> Err<T> of(T value) {
        return new Err<>(value);
    }

    public static <T> Err<T> of(Optional<T> optional) {
        return new Err<>(optional.orElse(null));
    }

    public static <T> Err<T> of(Err<T> optional) {
        return optional.map(Err::of).orElse(Err.failure());
    }

    public static <X,T> Err<T> from(Err<X> err) {
        return Err.failure(err.exception);
    }

    public Opt<T> toOpt() {
        return this.map(Opt::of).orElse(Opt.empty());
    }

    /**
     * Returns an {@code Err} describing the given value, if
     * non-{@code null}, otherwise returns an empty {@code Err}.
     *
     * @param value the possibly-{@code null} value to describe
     * @param <T> the type of the value
     * @return an {@code Err} with a present value if the specified value
     *         is non-{@code null}, otherwise an empty {@code Err}
     */
    public static <T> Err<T> ofNullable(T value) {
        return value == null ? failure() : of(value);
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @return the non-{@code null} value described by this {@code Err}
     * @throws NoSuchElementException if no value is present
     * @see Err#isPresent()
     * @deprecated use orPanic()
     */
    @Deprecated
    public T get() {
        if (value == null) {
            if (this.exception instanceof LorikeetException) {
                throw (LorikeetException)this.exception;
            }
            throw new RuntimeException(this.exception);
        }
        return value;
    }

    public T orPanic() {
        return this.get();
    }

    public boolean failedWith(Class<? extends Exception> failedWith) {
        if (this.exception == null || failedWith == null) {
            return false;
        }
        return failedWith.equals(this.exception.getClass());
    }

    public boolean failedWith(Exception failedWith) {
        if (this.exception == null || failedWith == null) {
            return false;
        }
        return failedWith.equals(this.exception);
    }

    /**
     * If a value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if a value is present, otherwise {@code false}
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param action the action to be performed, if a value is present
     * @deprecated use Err#then instead
     * @throws NullPointerException if value is present and the given action is
     *         {@code null}
     */
    @Deprecated
    public void ifPresent(Consumer<? super T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

    public Err<T> then(Consumer<? super T> action) {
        this.ifPresent(action);
        return this;
    }

    public Err<T> ifnot(Runnable runnable) {
        if (!this.isPresent()) {
            runnable.run();
        }
        return this;
    }

    public Err<T> ifso(Runnable runnable) {
        if (this.isPresent()) {
            runnable.run();
        }
        return this;
    }

    public <X> Err<X> pipe(Fun<T, Err<X>> functor) {
        if (!this.isPresent()) {
            return Err.failure(this.exception);
        }
        return functor.apply(this.value);
    }

    public Err<T> mapError(Exception exception) {
        if (this.isPresent()) {
            return this;
        }
        return Err.failure(exception);
    }

    public Opt<Exception> getException() {
        return Opt.ofNullable(this.exception);
    }


    /**
     * If a value is present, performs the given action with the value,
     * otherwise performs the given empty-based action.
     *
     * @param action the action to be performed, if a value is present
     * @param emptyAction the empty-based action to be performed, if no value is
     *        present
     * @throws NullPointerException if a value is present and the given action
     *         is {@code null}, or no value is present and the given empty-based
     *         action is {@code null}.
     * @since 9
     */
    public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (value != null) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * returns an {@code Err} describing the value, otherwise returns an
     * empty {@code Err}.
     *
     * @param predicate the predicate to apply to a value, if present
     * @return an {@code Err} describing the value of this
     *         {@code Err}, if a value is present and the value matches the
     *         given predicate, otherwise an empty {@code Err}
     * @throws NullPointerException if the predicate is {@code null}
     */
    public Err<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : failure();
        }
    }

    /**
     * If a value is present, returns an {@code Err} describing (as if by
     * {@link #ofNullable}) the result of applying the given mapping function to
     * the value, otherwise returns an empty {@code Err}.
     *
     * <p>If the mapping function returns a {@code null} result then this method
     * returns an empty {@code Err}.
     *
     * @apiNote
     * This method supports post-processing on {@code Err} values, without
     * the need to explicitly check for a return status.  For example, the
     * following code traverses a stream of URIs, selects one that has not
     * yet been processed, and creates a path from that URI, returning
     * an {@code Err<Path>}:
     *
     * <pre>{@code
     *     Err<Path> p =
     *         uris.stream().filter(uri -> !isProcessedYet(uri))
     *                       .findFirst()
     *                       .map(Paths::get);
     * }</pre>
     *
     * Here, {@code findFirst} returns an {@code Err<URI>}, and then
     * {@code map} returns an {@code Err<Path>} for the desired
     * URI if one exists.
     *
     * @param mapper the mapping function to apply to a value, if present
     * @param <U> The type of the value returned from the mapping function
     * @return an {@code Err} describing the result of applying a mapping
     *         function to the value of this {@code Err}, if a value is
     *         present, otherwise an empty {@code Err}
     * @throws NullPointerException if the mapping function is {@code null}
     */
    public <U> Err<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return failure();
        } else {
            return Err.ofNullable(mapper.apply(value));
        }
    }

    /**
     * If a value is present, returns the result of applying the given
     * {@code Err}-bearing mapping function to the value, otherwise returns
     * an empty {@code Err}.
     *
     * <p>This method is similar to {@link #map(Function)}, but the mapping
     * function is one whose result is already an {@code Err}, and if
     * invoked, {@code flatMap} does not wrap it within an additional
     * {@code Err}.
     *
     * @param <U> The type of value of the {@code Err} returned by the
     *            mapping function
     * @param mapper the mapping function to apply to a value, if present
     * @return the result of applying an {@code Err}-bearing mapping
     *         function to the value of this {@code Err}, if a value is
     *         present, otherwise an empty {@code Err}
     * @throws NullPointerException if the mapping function is {@code null} or
     *         returns a {@code null} result
     */
    public <U> Err<U> flatMap(Function<? super T, ? extends Err<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return failure();
        } else {
            @SuppressWarnings("unchecked")
            Err<U> r = (Err<U>) mapper.apply(value);
            return Objects.requireNonNull(r);
        }
    }

    /**
     * If a value is present, returns an {@code Err} describing the value,
     * otherwise returns an {@code Err} produced by the supplying function.
     *
     * @param supplier the supplying function that produces an {@code Err}
     *        to be returned
     * @return returns an {@code Err} describing the value of this
     *         {@code Err}, if a value is present, otherwise an
     *         {@code Err} produced by the supplying function.
     * @throws NullPointerException if the supplying function is {@code null} or
     *         produces a {@code null} result
     * @since 9
     */
    public Err<T> or(Supplier<? extends May<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            Err<T> r = (Err<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    /**
     * If a value is present, returns a sequential {@link Stream} containing
     * only that value, otherwise returns an empty {@code Stream}.
     *
     * @apiNote
     * This method can be used to transform a {@code Stream} of Err
     * elements to a {@code Stream} of present value elements:
     * <pre>{@code
     *     Stream<Err<T>> os = ..
     *     Stream<T> s = os.flatMap(Err::stream)
     * }</pre>
     *
     * @return the Err value as a {@code Stream}
     * @since 9
     */
    public Stream<T> stream() {
        if (!isPresent()) {
            return Stream.empty();
        } else {
            return Stream.of(value);
        }
    }

    /**
     * If a value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no value is present.
     *        May be {@code null}.
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        return value != null ? value : other;
    }

    /**
     * If a value is present, returns the value, otherwise returns the result
     * produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the value, if present, otherwise the result produced by the
     *         supplying function
     * @throws NullPointerException if no value is present and the supplying
     *         function is {@code null}
     */
    public T orElseGet(Supplier<? extends T> supplier) {
        return value != null ? value : supplier.get();
    }

    /**
     * If a value is present, returns the value, otherwise throws an exception
     * produced by the exception supplying function.
     *
     * @apiNote
     * A method reference to the exception constructor with an empty argument
     * list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     *
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier the supplying function that produces an
     *        exception to be thrown
     * @return the value, if present
     * @throws X if no value is present
     * @throws NullPointerException if no value is present and the exception
     *          supplying function is {@code null}
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this {@code Err}.
     * The other object is considered equal if:
     * <ul>
     * <li>it is also an {@code Err} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code equals()}.
     * </ul>
     *
     * @param obj an object to be tested for equality
     * @return {@code true} if the other object is "equal to" this object
     *         otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Err)) {
            return false;
        }

        Err<?> other = (Err<?>) obj;
        return Objects.equals(value, other.value);
    }

    /**
     * Returns the hash code of the value, if present, otherwise {@code 0}
     * (zero) if no value is present.
     *
     * @return hash code value of the present value or {@code 0} if no value is
     *         present
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * Returns a non-empty string representation of this {@code Err}
     * suitable for debugging.  The exact presentation format is unspecified and
     * may vary between implementations and versions.
     *
     * @implSpec
     * If a value is present the result must include its string representation
     * in the result.  Empty and present {@code Err}s must be unambiguously
     * differentiable.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return value != null
            ? String.format("Err[%s]", value)
            : "Err.empty";
    }

    public static class NoValueException extends Exception {

    }
}