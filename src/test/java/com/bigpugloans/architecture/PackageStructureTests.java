package com.bigpugloans.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.core.importer.ImportOption;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.bigpugloans.scoring", importOptions = ImportOption.DoNotIncludeTests.class)
public class PackageStructureTests {

    // Package Dependency Rules
    @ArchTest
    static final ArchRule packages_should_be_free_of_cycles = slices()
            .matching("com.bigpugloans.scoring.(*)..")
            .should().beFreeOfCycles();

    @ArchTest
    static final ArchRule cluster_packages_should_not_depend_on_each_other = slices()
            .matching("..domain.model.(*Cluster)..")
            .should().notDependOnEachOther();

    // Adapter Package Rules
    @ArchTest
    static final ArchRule driving_adapters_should_only_contain_driving_adapters = classes()
            .that().resideInAPackage("..adapter.driving..")
            .should().haveSimpleNameEndingWith("Listener")
            .orShould().haveSimpleNameEndingWith("Controller")
            .orShould().haveSimpleNameEndingWith("Handler");

    @ArchTest
    static final ArchRule driven_adapters_should_only_contain_driven_adapters = classes()
            .that().resideInAPackage("..adapter.driven..")
            .and().areNotEnums()
            .and().areNotAnnotations()
            .should().haveSimpleNameEndingWith("Adapter")
            .orShould().haveSimpleNameEndingWith("Repository")
            .orShould().haveSimpleNameEndingWith("Message")
            .orShould().haveSimpleNameEndingWith("Document");

    // Domain Package Rules - simplified
    @ArchTest
    static final ArchRule domain_model_should_not_contain_services = noClasses()
            .that().resideInAPackage("..domain.model..")
            .should().haveSimpleNameEndingWith("Service");

    @ArchTest
    static final ArchRule domain_services_should_only_contain_services = classes()
            .that().resideInAPackage("..domain.service..")
            .should().haveSimpleNameEndingWith("DomainService");

    // Application Package Rules
    @ArchTest
    static final ArchRule application_services_should_only_contain_application_services = classes()
            .that().resideInAPackage("..application.service..")
            .should().haveSimpleNameEndingWith("ApplicationService")
            .orShould().haveSimpleNameEndingWith("Service");

    @ArchTest
    static final ArchRule application_ports_should_only_contain_interfaces = classes()
            .that().resideInAPackage("..application.ports..")
            .should().beInterfaces();

    // Configuration Rules
    @ArchTest
    static final ArchRule configuration_should_be_separate_from_business_logic = noClasses()
            .that().resideInAPackage("..domain..")
            .or().resideInAPackage("..application..")
            .should().dependOnClassesThat().haveSimpleNameEndingWith("Configuration");

    // Spring Annotation Rules
    @ArchTest
    static final ArchRule spring_annotations_should_not_be_in_domain = noClasses()
            .that().resideInAPackage("..domain..")
            .should().beAnnotatedWith("org.springframework.stereotype.Component")
            .orShould().beAnnotatedWith("org.springframework.stereotype.Service")
            .orShould().beAnnotatedWith("org.springframework.stereotype.Repository");
}