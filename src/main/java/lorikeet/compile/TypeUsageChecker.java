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

/*
* Checks if the usage of a type does exists
*/
public class TypeUsageChecker {

    private static final Sdk sdk = new Sdk();

    public List<CompileError> check(LorikeetSource source) {
        TypeRegistry registry = new TypeRegistry();
        registry.enter(sdk.getTypes());
        List<CompileError> errors = populate(registry, source);
        errors.addAll(typeCheck(source, registry));
        return errors;
    }

    private List<CompileError> typeCheck(LorikeetSource source, TypeRegistry registry) {
        List<CompileError> errors = new ArrayList<CompileError>();
        for (SourceFile sourceFile: source.getResults()) {
            errors.addAll(typeCheckSourceFile(sourceFile, registry));
        }
        return errors;
    }

    private List<CompileError> typeCheckSourceFile(SourceFile sourceFile, TypeRegistry registry) {
        return Stream.concat(
            typeCheckStructs(sourceFile, registry),
            typeCheckModules(sourceFile, registry)
        ).collect(Collectors.toList());
    }

    private Stream<CompileError> typeCheckStructs(SourceFile sourceFile, TypeRegistry registry) {
        return sourceFile.getStructs()
            .stream()
            .flatMap(struct -> typeCheck(sourceFile, struct, registry));
    }

    private Stream<CompileError> typeCheckModules(SourceFile sourceFile, TypeRegistry registry) {
        return sourceFile.getModules()
            .stream()
            .flatMap(module -> typeCheck(sourceFile, module, registry));
    }

    private Stream<CompileError> typeCheck(SourceFile sourceFile, LorikeetType type, TypeRegistry registry) {
        return Stream.concat(
            typeCheckAttributes(sourceFile, type.getAttributes(), registry),
            typeCheckFunctions(sourceFile, type, registry)
        );
    }

    private Stream<CompileError> typeCheckAttributes(SourceFile sourceFile, Set<Attribute> attrs, TypeRegistry registry) {
        final Stream.Builder<CompileError> errors = Stream.builder();
        for (Attribute attr : attrs) {
            if (!registry.get(attr.getType()).isPresent()) {
                errors.accept(new UnkownType(attr.getType(), sourceFile));
            }
        }
        return errors.build();
    }

    private Stream<CompileError> typeCheckFunctions(SourceFile sourceFile, LorikeetType type, TypeRegistry registry) {
        Stream<CompileError> errors = Stream.empty();
        for (Function func : type.getFunctions()) {
            errors = Stream.concat(errors, typeCheckFunction(sourceFile, func, registry));
        }
        return errors;
    }

    private Stream<CompileError> typeCheckFunction(SourceFile sourceFile, Function func, TypeRegistry registry) {
        final Stream.Builder<CompileError> errors = Stream.builder();
        if (!registry.get(func.getReturnType()).isPresent()) {
                errors.accept(new UnkownType(func.getReturnType(), sourceFile));
        }
        return Stream.concat(
            errors.build(),
            typeCheckAttributes(sourceFile, func.getAttributes(), registry)
        );
    }

    private List<CompileError> populate(TypeRegistry registry, LorikeetSource source) {
        final List<CompileError> errors = new ArrayList<CompileError>();

        for (SourceFile sourceFile : source.getResults()) {
            registry.enter(sourceFile).ifPresent(error -> errors.add(error));
        }

        return errors;
    }

}
