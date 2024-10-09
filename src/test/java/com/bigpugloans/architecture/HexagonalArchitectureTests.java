package com.bigpugloans.architecture;

import org.junit.jupiter.api.Test;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

public class HexagonalArchitectureTests {
    @Test
    void hexagonalTestScoring() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.bigpugloans.scoring");
        onionArchitecture()
                .domainModels("com.bigpugloans.scoring.domainmodel..")
                .applicationServices("com.bigpugloans.scoring.application..")
                .check(importedClasses);

    }
}
