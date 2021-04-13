package tech.leassets;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("tech.leassets");

        noClasses()
            .that()
                .resideInAnyPackage("tech.leassets.service..")
            .or()
                .resideInAnyPackage("tech.leassets.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..tech.leassets.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
