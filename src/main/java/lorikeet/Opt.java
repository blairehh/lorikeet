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
    https://github.com/netroby/jdk9-dev/blob/master/jdk/src/java.base/share/classes/java/util/Optional.java

    LORIKEET CHANGES:
    repackaged from java.util. to lorikeet
    renamed class from Optional to Opt
    deprecated get for orPanic
    deprecated ifPresent for then
    added Opt::of(Optional)
    implements May<T>
 */

package lorikeet;

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
 * {@code Opt} may have unpredictable results and should be avoided.
 *
 * @param <T> the type of value
 * @since 1.8
 */
public final class Opt<T> implements May<T> {
    /**
     * Common instance for {@code empty()}.
     */
    private static final Opt<?> EMPTY = new Opt<>();

    /**
     * If non-null, the value; if null, indicates no value is present
     */
    private final T value;

    /**
     * Constructs an empty instance.
     *
     * @implNote Generally only one empty instance, {@link Opt#EMPTY},
     * should exist per VM.
     */
    private Opt() {
        this.value = null;
    }

    /**
     * Returns an empty {@code Opt} instance.  No value is present for this
     * {@code Opt}.
     *
     * @apiNote
     * Though it may be tempting to do so, avoid articletesting if an object is empty
     * by comparing with {@code ==} against instances returned by
     * {@code Opt.empty()}.  There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     *
     * @param <T> The type of the non-existent value
     * @return an empty {@code Opt}
     */
    public static<T> Opt<T> empty() {
        @SuppressWarnings("unchecked")
        Opt<T> t = (Opt<T>) EMPTY;
        return t;
    }

    /**
     * Constructs an instance with the described value.
     *
     * @param value the non-{@code null} value to describe
     * @throws NullPointerException if value is {@code null}
     */
    private Opt(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Returns an {@code Opt} describing the given non-{@code null}
     * value.
     *
     * @param value the value to describe, which must be non-{@code null}
     * @param <T> the type of the value
     * @return an {@code Opt} with the value present
     * @throws NullPointerException if value is {@code null}
     */
    public static <T> Opt<T> of(T value) {
        return new Opt<>(value);
    }

    public static <T> Opt<T> of(Optional<T> optional) {
        return new Opt<>(optional.orElse(null));
    }

    /**
     * Returns an {@code Opt} describing the given value, if
     * non-{@code null}, otherwise returns an empty {@code Opt}.
     *
     * @param value the possibly-{@code null} value to describe
     * @param <T> the type of the value
     * @return an {@code Opt} with a present value if the specified value
     *         is non-{@code null}, otherwise an empty {@code Opt}
     */
    public static <T> Opt<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @return the non-{@code null} value described by this {@code Opt}
     * @throws NoSuchElementException if no value is present
     * @see Opt#isPresent()
     * @deprecated use orPanic()
     */
    @Deprecated
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public T orPanic() {
        return this.get();
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
     * @deprecated use Opt#then instead
     * @throws NullPointerException if value is present and the given action is
     *         {@code null}
     */
    @Deprecated
    public void ifPresent(Consumer<? super T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

    public Opt<T> then(Consumer<? super T> action) {
        this.ifPresent(action);
        return this;
    }

    public <X> Opt<X> pipe(Fun<T, Opt<X>> fun) {
        if (!this.isPresent()) {
            return Opt.empty();
        }

        return fun.apply(this.value);
    }

    public T orNull() {
        if (this.isPresent()) {
            return this.value;
        }
        return null;
    }

    public Opt<T> ifnot(Runnable runnable) {
        if (!this.isPresent()) {
            runnable.run();
        }
        return this;
    }

    public Opt<T> ifso(Runnable runnable) {
        if (this.isPresent()) {
            runnable.run();
        }
        return this;
    }

    public Err<T> asErr(Exception e) {
        if (this.value == null) {
            return Err.failure(e);
        }
        return Err.of(this.value);
    }

    public Err<T> asErr() {
        if (this.value == null) {
            return Err.failure();
        }
        return Err.of(this.value);
    }

    public Err<T> asErr(Err<T> err) {
        if (this.value == null) {
            return err;
        }
        return Err.of(this.value);
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
     * returns an {@code Opt} describing the value, otherwise returns an
     * empty {@code Opt}.
     *
     * @param predicate the predicate to apply to a value, if present
     * @return an {@code Opt} describing the value of this
     *         {@code Opt}, if a value is present and the value matches the
     *         given predicate, otherwise an empty {@code Opt}
     * @throws NullPointerException if the predicate is {@code null}
     */
    public Opt<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : empty();
        }
    }

    /**
     * If a value is present, returns an {@code Opt} describing (as if by
     * {@link #ofNullable}) the result of applying the given mapping function to
     * the value, otherwise returns an empty {@code Opt}.
     *
     * <p>If the mapping function returns a {@code null} result then this method
     * returns an empty {@code Opt}.
     *
     * @apiNote
     * This method supports post-processing on {@code Opt} values, without
     * the need to explicitly check for a return status.  For example, the
     * following code traverses a stream of URIs, selects one that has not
     * yet been processed, and creates a path from that URI, returning
     * an {@code Opt<Path>}:
     *
     * <pre>{@code
     *     Opt<Path> p =
     *         uris.stream().filter(uri -> !isProcessedYet(uri))
     *                       .findFirst()
     *                       .map(Paths::get);
     * }</pre>
     *
     * Here, {@code findFirst} returns an {@code Opt<URI>}, and then
     * {@code map} returns an {@code Opt<Path>} for the desired
     * URI if one exists.
     *
     * @param mapper the mapping function to apply to a value, if present
     * @param <U> The type of the value returned from the mapping function
     * @return an {@code Opt} describing the result of applying a mapping
     *         function to the value of this {@code Opt}, if a value is
     *         present, otherwise an empty {@code Opt}
     * @throws NullPointerException if the mapping function is {@code null}
     */
    public <U> Opt<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return Opt.ofNullable(mapper.apply(value));
        }
    }

    /**
     * If a value is present, returns the result of applying the given
     * {@code Opt}-bearing mapping function to the value, otherwise returns
     * an empty {@code Opt}.
     *
     * <p>This method is similar to {@link #map(Function)}, but the mapping
     * function is one whose result is already an {@code Opt}, and if
     * invoked, {@code flatMap} does not wrap it within an additional
     * {@code Opt}.
     *
     * @param <U> The type of value of the {@code Opt} returned by the
     *            mapping function
     * @param mapper the mapping function to apply to a value, if present
     * @return the result of applying an {@code Opt}-bearing mapping
     *         function to the value of this {@code Opt}, if a value is
     *         present, otherwise an empty {@code Opt}
     * @throws NullPointerException if the mapping function is {@code null} or
     *         returns a {@code null} result
     */
    public <U> Opt<U> flatMap(Function<? super T, ? extends Opt<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            @SuppressWarnings("unchecked")
            Opt<U> r = (Opt<U>) mapper.apply(value);
            return Objects.requireNonNull(r);
        }
    }

    /**
     * If a value is present, returns an {@code Opt} describing the value,
     * otherwise returns an {@code Opt} produced by the supplying function.
     *
     * @param supplier the supplying function that produces an {@code Opt}
     *        to be returned
     * @return returns an {@code Opt} describing the value of this
     *         {@code Opt}, if a value is present, otherwise an
     *         {@code Opt} produced by the supplying function.
     * @throws NullPointerException if the supplying function is {@code null} or
     *         produces a {@code null} result
     * @since 9
     */
    public Opt<T> or(Supplier<? extends May<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            Opt<T> r = (Opt<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    /**
     * If a value is present, returns a sequential {@link Stream} containing
     * only that value, otherwise returns an empty {@code Stream}.
     *
     * @apiNote
     * This method can be used to transform a {@code Stream} of Opt
     * elements to a {@code Stream} of present value elements:
     * <pre>{@code
     *     Stream<Opt<T>> os = ..
     *     Stream<T> s = os.flatMap(Opt::stream)
     * }</pre>
     *
     * @return the Opt value as a {@code Stream}
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
     * Indicates whether some other object is "equal to" this {@code Opt}.
     * The other object is considered equal if:
     * <ul>
     * <li>it is also an {@code Opt} and;
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

        if (!(obj instanceof Opt)) {
            return false;
        }

        Opt<?> other = (Opt<?>) obj;
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
     * Returns a non-empty string representation of this {@code Opt}
     * suitable for debugging.  The exact presentation format is unspecified and
     * may vary between implementations and versions.
     *
     * @implSpec
     * If a value is present the result must include its string representation
     * in the result.  Empty and present {@code Opt}s must be unambiguously
     * differentiable.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return value != null
            ? String.format("Opt[%s]", value)
            : "Opt.empty";
    }
}
