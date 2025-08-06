package com.bigpugloans.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.core.importer.ImportOption;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * Educational Architecture Tests demonstrating Hexagonal Architecture principles
 * 
 * Key Learning Points:
 * - Domain models are at the center and can be accessed by all layers
 * - Repository interfaces belong to the domain (DDD principle)
 * - External systems can use domain objects (conformist pattern)
 * - Infrastructure dependencies point inward toward the domain
 */
@AnalyzeClasses(packages = "com.bigpugloans.scoring", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalArchitectureTests {

    // Internal Hexagonal Architecture - Domain models can be accessed by internal layers only
    // External access (like events using domain models) is allowed via conformist pattern
    @ArchTest
    static final ArchRule internal_layers_respect_dependency_direction = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("DomainModel").definedBy("..domain.model..")
            .layer("DomainServices").definedBy("..domain.service..")
            .layer("ApplicationServices").definedBy("..application.service..")
            .layer("ApplicationPorts").definedBy("..application.ports..")
            .layer("DrivingAdapters").definedBy("..adapter.driving..")
            .layer("DrivenAdapters").definedBy("..adapter.driven..")

            .whereLayer("DomainServices").mayOnlyBeAccessedByLayers("ApplicationServices", "ApplicationPorts")
            .whereLayer("ApplicationServices").mayOnlyBeAccessedByLayers("DrivingAdapters")
            .whereLayer("ApplicationPorts").mayOnlyBeAccessedByLayers("ApplicationServices", "DrivingAdapters", "DrivenAdapters")
            .whereLayer("DrivingAdapters").mayNotBeAccessedByAnyLayer()
            .whereLayer("DrivenAdapters").mayNotBeAccessedByAnyLayer();

    @ArchTest
    static final ArchRule domain_core_should_not_depend_on_infrastructure = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..adapter..", 
                    "org.springframework..", 
                    "org.mongodb..",
                    "jakarta.persistence.."
            );

    @ArchTest
    static final ArchRule application_core_should_not_depend_on_infrastructure = noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..adapter..", 
                    "org.mongodb..",
                    "jakarta.persistence.."
            );

    @ArchTest
    static final ArchRule infrastructure_should_depend_on_ports = classes()
            .that().resideInAPackage("..adapter..")
            .and().areNotAnnotations()
            .and().areNotEnums()  
            .and().areNotInterfaces()
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..application.ports..",
                    "..domain.."
            );

    @ArchTest
    static final ArchRule driving_adapters_should_use_driving_ports = classes()
            .that().resideInAPackage("..adapter.driving..")
            .should().dependOnClassesThat().resideInAPackage("..application.ports.driving..");

    // Educational Example: Driven adapters can depend on domain repositories (DDD pattern)
    // This demonstrates that repository implementations (in adapters) implement domain contracts
    @ArchTest
    static final ArchRule driven_adapters_should_depend_on_domain_contracts = classes()
            .that().resideInAPackage("..adapter.driven..")
            .and().areNotAnnotations()
            .and().areNotEnums()
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..application.ports.driven..", 
                    "..domain.model.."
            )
            .because("Driven adapters implement domain contracts or application ports");
}