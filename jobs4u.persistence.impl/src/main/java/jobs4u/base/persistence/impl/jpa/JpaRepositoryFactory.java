/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package jobs4u.base.persistence.impl.jpa;

import jobs4u.base.Application;
import jobs4u.base.candidateManagement.application.repositories.CandidateRepository;
import jobs4u.base.clientManagement.application.repositories.ClientRepository;
import jobs4u.base.notificationManagement.repositories.NotificationRepository;
import jobs4u.base.pluginManagement.repositories.InterviewModelSpecificationRepository;
import jobs4u.base.jobApplications.repositories.JobApplicationRepository;
import jobs4u.base.jobOpeningsManagement.repositories.JobOpeningRepository;
import jobs4u.base.pluginManagement.repositories.JobRequirementSpecificationRepository;
import jobs4u.base.jobs4uusermanagement.repositories.SignupRequestRepository;
import jobs4u.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.jpa.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 *
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

    @Override
    public UserRepository users(final TransactionalContext autoTx) {
        return new JpaAutoTxUserRepository(autoTx);
    }

    @Override
    public UserRepository users() {
        return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }


    @Override
    public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
        return new JpaClientUserRepository(autoTx);
    }

    @Override
    public JpaClientUserRepository clientUsers() {
        return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
    }


    @Override
    public JobOpeningRepository jobOpenings() {
        return new JpaJobOpeningRepository();
    }

    @Override
    public JobApplicationRepository jobApplications(final TransactionalContext autoTx) {
        return new JpaJobApplicationRepository(autoTx);
    }

    @Override
    public JobApplicationRepository jobApplications() {
        return new JpaJobApplicationRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public NotificationRepository notifications() {
        return new JpaNotificationRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public NotificationRepository notifications(final TransactionalContext autoTx) {
        return new JpaNotificationRepository(autoTx);
    }



    @Override
    public ClientRepository clients() {
        return new JpaClientRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public ClientRepository clients(TransactionalContext autoTx) {
        return new JpaClientRepository(autoTx);
    }


    @Override
    public CandidateRepository candidates(final TransactionalContext autoTx) {
        return new JpaCandidateRepository(autoTx);
    }

    @Override
    public CandidateRepository candidates() {
        return new JpaCandidateRepository(Application.settings().getPersistenceUnitName());
    }



    @Override
    public JpaJobRequirementSpecificationRepository jobRequirementsSpecification(final TransactionalContext autoTx) {
        return new JpaJobRequirementSpecificationRepository(autoTx);
    }

    @Override
    public JpaJobRequirementSpecificationRepository jobRequirementsSpecification() {
        return new JpaJobRequirementSpecificationRepository(Application.settings().getPersistenceUnitName());
    }



    @Override
    public InterviewModelSpecificationRepository interviewModelsSpecification() {
        return new JpaInterviewModelSpecificationRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public InterviewModelSpecificationRepository interviewModelsSpecification(TransactionalContext autoTx) {
        return new JpaInterviewModelSpecificationRepository(autoTx);
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

}
