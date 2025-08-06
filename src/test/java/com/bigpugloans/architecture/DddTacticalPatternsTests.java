package com.bigpugloans.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.core.importer.ImportOption;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.ValueObject;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(packages = "com.bigpugloans.scoring.domain", importOptions = ImportOption.DoNotIncludeTests.class)
public class DddTacticalPatternsTests {

    // Aggregate Root Rules
    @ArchTest
    static final ArchRule aggregates_should_be_in_domain_model = classes()
            .that().areAnnotatedWith(AggregateRoot.class)
            .should().resideInAPackage("..domain.model..");

    // Domain Service Rules
    @ArchTest
    static final ArchRule domain_services_should_only_depend_on_domain = classes()
            .that().resideInAPackage("..domain.service..")
            .should().onlyDependOnClassesThat().resideInAnyPackage(
                    "..domain..",
                    "java..",
                    "org.jmolecules..",
                    "javax.annotation.."
            );

    // Event Rules
    @ArchTest
    static final ArchRule domain_events_should_be_in_domain_model = classes()
            .that().haveSimpleNameEndingWith("Event")
            .and().resideInAPackage("..domain..")
            .should().resideInAPackage("..domain.model..");

    // Value Object Rules
    @ArchTest
    static final ArchRule value_objects_should_be_in_domain = classes()
            .that().areAnnotatedWith(ValueObject.class)
            .should().resideInAPackage("..domain.model..");
}