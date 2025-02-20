package com.bigpugloans.architecture;

import com.tngtech.archunit.junit.*;
import com.tngtech.archunit.lang.*;

import org.jmolecules.archunit.*;
@AnalyzeClasses(packages = "com.bigpugloans.scoring") // (1)
public class JMoleculesArchUnitTests {

    @ArchTest ArchRule dddRules = JMoleculesDddRules.all();
    @ArchTest ArchRule hexagonalRules = JMoleculesArchitectureRules.ensureHexagonal();
    @ArchTest ArchRule onionRules = JMoleculesArchitectureRules.ensureOnionClassical();

}
