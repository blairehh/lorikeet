package lorikeet.compile;

import lorikeet.error.CompileError;
import lorikeet.error.UnkownType;
import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import lorikeet.lang.LorikeetSource;
import lorikeet.lang.LorikeetType;
import lorikeet.lang.SourceFile;
import lorikeet.sdk.Sdk;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class TypeChecker {

    private static final Sdk sdk = new Sdk();

    public List<CompileError> check(LorikeetSource source) {
        TypeDirectory directory = new TypeDirectory();
        directory.enter(sdk.getTypes());
        List<CompileError> errors = populate(directory, source);
        errors.addAll(typeCheck(source, directory));
        return errors;
    }

    private List<CompileError> typeCheck(LorikeetSource source, TypeDirectory directory) {
        List<CompileError> errors = new ArrayList<CompileError>();
        for (SourceFile sourceFile: source.getResults()) {
            errors.addAll(typeCheckSourceFile(sourceFile, directory));
        }
        return errors;
    }

    private List<CompileError> typeCheckSourceFile(SourceFile sourceFile, TypeDirectory directory) {
        return Stream.concat(
            typeCheckStructs(sourceFile, directory),
            typeCheckModules(sourceFile, directory)
        ).collect(Collectors.toList());
    }

    private Stream<CompileError> typeCheckStructs(SourceFile sourceFile, TypeDirectory directory) {
        return sourceFile.getStructs()
            .stream()
            .flatMap(struct -> typeCheck(sourceFile, struct, directory));
    }

    private Stream<CompileError> typeCheckModules(SourceFile sourceFile, TypeDirectory directory) {
        return sourceFile.getModules()
            .stream()
            .flatMap(module -> typeCheck(sourceFile, module, directory));
    }

    private Stream<CompileError> typeCheck(SourceFile sourceFile, LorikeetType type, TypeDirectory directory) {
        return Stream.concat(
            typeCheckAttributes(sourceFile, type.getAttributes(), directory),
            typeCheckFunctions(sourceFile, type, directory)
        );
    }

    private Stream<CompileError> typeCheckAttributes(SourceFile sourceFile, Set<Attribute> attrs, TypeDirectory directory) {
        final Stream.Builder<CompileError> errors = Stream.builder();
        for (Attribute attr : attrs) {
            if (!directory.get(attr.getType()).isPresent()) {
                errors.accept(new UnkownType(attr.getType(), sourceFile));
            }
        }
        return errors.build();
    }

    private Stream<CompileError> typeCheckFunctions(SourceFile sourceFile, LorikeetType type, TypeDirectory directory) {
        Stream<CompileError> errors = Stream.empty();
        for (Function func : type.getFunctions()) {
            errors = Stream.concat(errors, typeCheckFunction(sourceFile, func, directory));
        }
        return errors;
    }

    private Stream<CompileError> typeCheckFunction(SourceFile sourceFile, Function func, TypeDirectory directory) {
        final Stream.Builder<CompileError> errors = Stream.builder();
        if (!directory.get(func.getReturnType()).isPresent()) {
                errors.accept(new UnkownType(func.getReturnType(), sourceFile));
        }
        return Stream.concat(
            errors.build(),
            typeCheckAttributes(sourceFile, func.getAttributes(), directory)
        );
    }

    private List<CompileError> populate(TypeDirectory directory, LorikeetSource source) {
        final List<CompileError> errors = new ArrayList<CompileError>();

        for (SourceFile sourceFile : source.getResults()) {
            directory.enter(sourceFile).ifPresent(error -> errors.add(error));
        }

        return errors;
    }

}
