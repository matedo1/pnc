/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.rest.restmodel;

import static org.jboss.pnc.rest.utils.StreamHelper.nullableStreamOf;
import static org.jboss.pnc.rest.utils.Utility.performIfNotNull;

import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.ProductVersion;
import org.jboss.pnc.rest.validation.groups.WhenCreatingNew;
import org.jboss.pnc.rest.validation.groups.WhenUpdating;
import org.jboss.pnc.rest.validation.validators.ScmUrl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "Configuration")
public class BuildConfigurationRest implements GenericRestEntity<Integer> {

    @NotNull(groups = WhenUpdating.class)
    @Null(groups = WhenCreatingNew.class)
    private Integer id;

    @NotNull(groups = WhenCreatingNew.class)
    @Pattern(regexp = "^[a-zA-Z0-9_.][a-zA-Z0-9_.-]*(?<!\\.git)$", groups = { WhenCreatingNew.class, WhenUpdating.class })
    private String name;

    private String description;

    private String buildScript;

    @NotNull(groups = WhenCreatingNew.class)
    @ScmUrl(groups = { WhenCreatingNew.class, WhenUpdating.class })
    private String scmRepoURL;

    private String scmRevision;

    @ScmUrl(groups = { WhenCreatingNew.class, WhenUpdating.class })
    @Getter
    @Setter
    private String scmExternalRepoURL;

    @Getter
    @Setter
    private String scmExternalRevision;

    private Date creationTime;

    private Date lastModificationTime;

    private boolean archived;

    @NotNull(groups = WhenCreatingNew.class)
    private ProjectRest project;

    @NotNull(groups = { WhenCreatingNew.class, WhenUpdating.class })
    private BuildEnvironmentRest environment;

    private Set<Integer> dependencyIds;

    private Integer productVersionId;

    @Getter
    @Setter
    private Map<String, String> genericParameters ;

    public BuildConfigurationRest() {
    }

    public BuildConfigurationRest(BuildConfiguration buildConfiguration) {
        this.id = buildConfiguration.getId();
        this.name = buildConfiguration.getName();
        this.description = buildConfiguration.getDescription();
        this.buildScript = buildConfiguration.getBuildScript();
        this.scmRepoURL = buildConfiguration.getScmRepoURL();
        this.scmRevision = buildConfiguration.getScmRevision();
        this.scmExternalRepoURL = buildConfiguration.getScmExternalRepoURL();
        this.scmExternalRevision = buildConfiguration.getScmExternalRevision();
        this.creationTime = buildConfiguration.getCreationTime();
        this.lastModificationTime = buildConfiguration.getLastModificationTime();
        this.archived = buildConfiguration.isArchived();
        this.genericParameters = buildConfiguration.getGenericParameters();
        performIfNotNull(buildConfiguration.getProject(),
                () -> this.project = new ProjectRest(buildConfiguration.getProject()));
        performIfNotNull(buildConfiguration.getBuildEnvironment(),
                () -> this.environment = new BuildEnvironmentRest(buildConfiguration.getBuildEnvironment()));
        this.dependencyIds = nullableStreamOf(buildConfiguration.getDependencies())
                .map(dependencyConfig -> dependencyConfig.getId()).collect(Collectors.toSet());
        performIfNotNull(buildConfiguration.getProductVersion(),
                () -> this.productVersionId = buildConfiguration.getProductVersion().getId());
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuildScript() {
        return buildScript;
    }

    public void setBuildScript(String buildScript) {
        this.buildScript = buildScript;
    }

    public String getScmRepoURL() {
        return scmRepoURL;
    }

    public void setScmRepoURL(String scmRepoURL) {
        this.scmRepoURL = scmRepoURL;
    }

    public String getScmRevision() {
        return scmRevision;
    }

    public void setScmRevision(String scmRevision) {
        this.scmRevision = scmRevision;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Date lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public ProjectRest getProject() {
        return project;
    }

    public void setProject(ProjectRest project) {
        this.project = project;
    }

    public Integer getProductVersionId() {
        return productVersionId;
    }

    public void setProductVersionId(Integer productVersionId) {
        this.productVersionId = productVersionId;
    }

    public Set<Integer> getDependencyIds() {
        return dependencyIds;
    }

    public void setDependencyIds(Set<Integer> dependencyIds) {
        this.dependencyIds = dependencyIds;
    }

    public boolean addDependency(Integer dependencyId) {
        return dependencyIds.add(dependencyId);
    }

    public boolean removeDependency(Integer dependencyId) {
        return dependencyIds.remove(dependencyId);
    }

    public BuildEnvironmentRest getEnvironment() {
        return environment;
    }

    public void setEnvironment(BuildEnvironmentRest environment) {
        this.environment = environment;
    }

    public BuildConfiguration.Builder toDBEntityBuilder() {
        BuildConfiguration.Builder builder = BuildConfiguration.Builder.newBuilder()
                .id(this.getId())
                .name(this.getName())
                .description(this.getDescription())
                .buildScript(this.getBuildScript())
                .scmRepoURL(this.getScmRepoURL())
                .scmRevision(this.getScmRevision())
                .scmExternalRepoURL(this.getScmExternalRepoURL())
                .scmExternalRevision(this.getScmExternalRevision())
                .archived(this.isArchived())
                .genericParameters(this.getGenericParameters());

        performIfNotNull(this.getProject(), () -> builder.project(this.getProject().toDBEntityBuilder().build()));
        performIfNotNull(this.getEnvironment(), () -> builder.buildEnvironment(this.getEnvironment().toDBEntityBuilder().build()));
        performIfNotNull(this.getProductVersionId(), () -> builder.productVersion(ProductVersion.Builder.newBuilder().id(productVersionId).build()));

        nullableStreamOf(this.getDependencyIds()).forEach(dependencyId -> {
            BuildConfiguration.Builder buildConfigurationBuilder = BuildConfiguration.Builder.newBuilder().id(dependencyId);
            builder.dependency(buildConfigurationBuilder.build());
        });
        
        return builder;
    }
}
