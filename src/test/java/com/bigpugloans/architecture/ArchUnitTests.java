package com.bigpugloans.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.jmolecules.ddd.annotation.AggregateRoot;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "com.bigpugloans.scoring")
public class ArchUnitTests {
    @ArchTest
    static final ArchRule onion_architecture_is_respected = onionArchitecture()
            .domainModels("..domain.model..")
            .domainServices("..domain.service..")
            .applicationServices("..application..")
            .adapter("driving", "..adapter.driving..")
            .adapter("driven", "..adapter.driven..");

    @ArchTest
    static final ArchRule aggregates_should_not_depend_on_each_other = classes()
            .that().areAnnotatedWith(AggregateRoot.class)
            .should().dependOnClassesThat().areNotAnnotatedWith(AggregateRoot.class);


}
