package jobs4u.base.jobRequirement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import jobs4u.base.jobRequirement.domain.JobRequirementSpecification;
import jobs4u.base.jobRequirement.domain.JobRequirementSpecificationIdentifier;
import jobs4u.base.jobRequirement.domain.JobRequirementSpecificationJarFile;
import jobs4u.base.jobRequirement.repositories.JobRequirementSpecificationRepository;
import jobs4u.base.usermanagement.domain.Jobs4uRoles;

public class ConfigureJobRequirementPluginController {
    private final JobRequirementSpecificationRepository theRepository;
    private final AuthorizationService authz;
    public ConfigureJobRequirementPluginController(JobRequirementSpecificationRepository theRepository, AuthorizationService authz){
        this.theRepository = theRepository;
        this.authz = authz;
    }

    public JobRequirementSpecification configurePlugin(String identifier, String jarFile){
        authz.ensureAuthenticatedUserHasAnyOf(Jobs4uRoles.POWER_USER, Jobs4uRoles.ADMIN, Jobs4uRoles.LANGUAGE_ENGINEER);

        JobRequirementSpecificationIdentifier jobRequirementSpecificationIdentifier = JobRequirementSpecificationIdentifier.valueOf(identifier);
        JobRequirementSpecificationJarFile jobRequirementSpecificationJarFile = JobRequirementSpecificationJarFile.valueOf(jarFile);
        JobRequirementSpecification specification = new JobRequirementSpecification(jobRequirementSpecificationIdentifier, jobRequirementSpecificationJarFile);

        return theRepository.save(specification);
    }
}