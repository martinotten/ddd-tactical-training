package com.bigpugloans.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Repository;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import com.tngtech.archunit.core.importer.ImportOption;

/**
 * Educational DDD Architecture Tests using ArchUnit
 * 
 * Demonstrates key architectural patterns:
 * - Onion Architecture with domain at the center
 * - Repository interfaces in domain layer (DDD principle)
 * - Domain model independence from frameworks
 * - Proper dependency inversion
 * - Conformist pattern allowing external domain usage
 * 
 * This serves as both architecture validation and learning example
 */
@AnalyzeClasses(packages = "com.bigpugloans", importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class ArchUnitTests {
    
    // Educational Example: Core Onion Architecture Principles
    // This demonstrates the essential onion architecture rule: dependencies point inward
    @ArchTest
    static final ArchRule onion_architecture_domain_isolation = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage("..application..", "..adapter..")
            .because("Domain layer should not depend on outer layers - core onion architecture principle");

    @ArchTest
    static final ArchRule onion_architecture_application_isolation = noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat().resideInAPackage("..adapter..")
            .because("Application layer should not depend on adapter layer - onion architecture principle");

    // Educational Example: External Domain Usage (Conformist Pattern)
    // This shows how external systems can use domain objects directly
    // Note: This is analyzed separately as it's outside the internal onion
    @ArchTest
    static final ArchRule external_systems_may_use_domain_via_conformist_pattern = classes()
            .that().resideInAPackage("com.bigpugloans.events..")
            .should().onlyDependOnClassesThat().resideInAnyPackage(
                    "com.bigpugloans.events..",
                    "com.bigpugloans.scoring.domain.model..",
                    "java..",
                    "org.springframework.."
            )
            .because("External systems can use domain objects via conformist pattern - this is outside our onion");

    // DDD Aggregate Rules
    @ArchTest
    static final ArchRule aggregates_should_not_depend_on_each_other = classes()
            .that().areAnnotatedWith(AggregateRoot.class)
            .should().onlyDependOnClassesThat().areNotAnnotatedWith(AggregateRoot.class);

    @ArchTest
    static final ArchRule aggregates_should_be_in_domain_model = classes()
            .that().areAnnotatedWith(AggregateRoot.class)
            .should().resideInAPackage("..domain.model..");

    // Domain Isolation Rules - Educational Examples
    // Domain layer should be infrastructure-agnostic and not depend on outer layers
    
    @ArchTest
    static final ArchRule domain_should_not_depend_on_application_services = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAPackage("..application.service..")
            .because("Domain should not know about application services - dependency inversion principle");

    @ArchTest
    static final ArchRule domain_should_not_depend_on_adapters = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAPackage("..adapter..")
            .because("Domain should be adapter-agnostic - clean architecture principle");

    @ArchTest
    static final ArchRule domain_should_not_depend_on_frameworks = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "org.springframework..",
                    "org.hibernate..",
                    "jakarta.persistence.."
            )
            .because("Domain should be framework-independent - DDD principle");

    // Application Layer Rules
    @ArchTest
    static final ArchRule application_should_not_depend_on_adapters = noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat().resideInAPackage("..adapter..");

    @ArchTest
    static final ArchRule application_services_should_be_annotated = classes()
            .that().resideInAPackage("..application.service..")
            .and().haveSimpleNameEndingWith("ApplicationService")
            .should().beAnnotatedWith(Service.class);

    // Repository Pattern Rules - Educational Examples
    // Repository interfaces belong to domain (DDD principle)
    // Repository implementations belong to infrastructure/adapters
    
    @ArchTest
    static final ArchRule domain_repository_interfaces_should_be_in_domain = classes()
            .that().haveSimpleNameEndingWith("Repository")
            .and().areInterfaces()
            .and().areNotAssignableTo("org.springframework.data.repository.Repository")
            .and().resideInAPackage("com.bigpugloans.scoring..")
            .should().resideInAPackage("..domain.model..")
            .because("Domain repository interfaces define business contracts - DDD principle");
            
    @ArchTest
    static final ArchRule repository_implementations_should_be_in_adapter_layer = classes()
            .that().haveSimpleNameEndingWith("Repository")
            .and().areNotInterfaces()
            .and().resideInAPackage("com.bigpugloans.scoring..")
            .and().resideOutsideOfPackage("..testinfrastructure..")
            .should().resideInAPackage("..adapter.driven..")
            .because("Repository implementations are infrastructure concerns");

    // Ports and Adapters Rules
    @ArchTest
    static final ArchRule driving_ports_should_be_interfaces = classes()
            .that().resideInAPackage("..application.ports.driving..")
            .should().beInterfaces();

    @ArchTest
    static final ArchRule driven_ports_should_be_interfaces = classes()
            .that().resideInAPackage("..application.ports.driven..")
            .should().beInterfaces();

    // Naming Convention Rules
    @ArchTest
    static final ArchRule domain_services_naming = classes()
            .that().resideInAPackage("..domain.service..")
            .should().haveSimpleNameEndingWith("DomainService");

    @ArchTest
    static final ArchRule application_services_naming = classes()
            .that().resideInAPackage("..application.service..")
            .should().haveSimpleNameEndingWith("ApplicationService");

    // Cluster Architecture Rules
    @ArchTest
    static final ArchRule clusters_should_be_independent = slices()
            .matching("..domain.model.(*Cluster)..")
            .should().notDependOnEachOther();

    // Simplified Event Rules
    @ArchTest
    static final ArchRule events_should_be_in_domain = classes()
            .that().haveSimpleNameEndingWith("Event")
            .should().resideInAPackage("..domain.model..");
}
