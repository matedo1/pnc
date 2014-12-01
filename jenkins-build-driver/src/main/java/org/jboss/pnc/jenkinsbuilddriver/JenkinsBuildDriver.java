package org.jboss.pnc.jenkinsbuilddriver;

import org.jboss.pnc.model.BuildType;
import org.jboss.pnc.model.ProjectBuildConfiguration;
import org.jboss.pnc.model.ProjectBuildResult;
import org.jboss.pnc.spi.builddriver.BuildDriver;
import org.jboss.pnc.spi.repositorymanager.RepositoryConfiguration;

import java.util.function.Consumer;

/**
 * Created by <a href="mailto:matejonnet@gmail.com">Matej Lazar</a> on 2014-11-23.
 */
// TODO implement me
public class JenkinsBuildDriver implements BuildDriver {
    private Consumer<ProjectBuildResult> onBuildComplete;

    @Override
    public String getDriverId() {
        return null;
    }

    @Override
    public void setRepository(RepositoryConfiguration deployRepository) {

    }

    @Override
    public void startProjectBuild(ProjectBuildConfiguration projectBuildConfiguration,
            Consumer<ProjectBuildResult> onBuildComplete) {

        this.onBuildComplete = onBuildComplete;

        return;
    }

    @Override
    public boolean canBuild(BuildType buildType) {
        return BuildType.JAVA.equals(buildType);
    }

    // TODO
    private void notifyBuildComplete() {
        // notify build complete

        onBuildComplete.accept(new ProjectBuildResult());
    }

}
