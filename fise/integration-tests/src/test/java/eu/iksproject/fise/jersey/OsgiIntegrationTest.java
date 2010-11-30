begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|jersey
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|Inject
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|Option
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|junit
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|junit
operator|.
name|JUnit4TestRunner
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Bundle
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|ServiceReference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|http
operator|.
name|HttpService
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|CoreOptions
operator|.
name|*
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|container
operator|.
name|def
operator|.
name|PaxRunnerOptions
operator|.
name|*
import|;
end_import

begin_class
annotation|@
name|RunWith
argument_list|(
name|JUnit4TestRunner
operator|.
name|class
argument_list|)
specifier|public
class|class
name|OsgiIntegrationTest
extends|extends
name|Assert
block|{
specifier|private
specifier|static
specifier|final
name|String
name|JERSEY_VERSION
init|=
literal|"1.2-SNAPSHOT"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|IKS_VERSION
init|=
literal|"0.9-SNAPSHOT"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PAX_WEB_VERSION
init|=
literal|"0.7.1"
decl_stmt|;
annotation|@
name|Inject
name|BundleContext
name|bundleContext
decl_stmt|;
annotation|@
name|Configuration
specifier|public
specifier|static
name|Option
index|[]
name|configure
parameter_list|()
block|{
return|return
name|options
argument_list|(
name|logProfile
argument_list|()
argument_list|,
name|systemProperty
argument_list|(
literal|"org.ops4j.pax.logging.DefaultServiceLog.level"
argument_list|)
operator|.
name|value
argument_list|(
literal|"WARN"
argument_list|)
argument_list|,
comment|// Explicit deps. TODO: there is probably a way to generate this from POM.
name|repositories
argument_list|(
literal|"http://repo1.maven.org/maven2"
argument_list|,
literal|"http://repository.apache.org/content/groups/snapshots-group"
argument_list|,
literal|"http://repository.ops4j.org/maven2"
argument_list|,
literal|"http://svn.apache.org/repos/asf/servicemix/m2-repo"
argument_list|,
literal|"http://repository.springsource.com/maven/bundles/release"
argument_list|,
literal|"http://repository.springsource.com/maven/bundles/external"
argument_list|,
literal|"http://download.java.net/maven/2"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.felix"
argument_list|,
literal|"org.apache.felix.scr"
argument_list|,
literal|"1.4.0"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.osgi"
argument_list|,
literal|"org.osgi.compendium"
argument_list|,
literal|"4.2.0"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"eu.iksproject"
argument_list|,
literal|"eu.iksproject.fise.servicesapi"
argument_list|,
name|IKS_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"eu.iksproject"
argument_list|,
literal|"eu.iksproject.fise.jobmanager"
argument_list|,
name|IKS_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"eu.iksproject"
argument_list|,
literal|"eu.iksproject.fise.standalone"
argument_list|,
name|IKS_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"eu.iksproject"
argument_list|,
literal|"eu.iksproject.fise.jersey"
argument_list|,
name|IKS_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.ops4j.pax.logging"
argument_list|,
literal|"pax-logging-api"
argument_list|,
literal|"1.4"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.ops4j.pax.logging"
argument_list|,
literal|"pax-logging-service"
argument_list|,
literal|"1.4"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.felix"
argument_list|,
literal|"org.apache.felix.configadmin"
argument_list|,
literal|"1.2.4"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.felix"
argument_list|,
literal|"org.apache.felix.http.bundle"
argument_list|,
literal|"2.0.4"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"javax.ws.rs"
argument_list|,
literal|"jsr311-api"
argument_list|,
literal|"1.1.1"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"com.sun.jersey"
argument_list|,
literal|"jersey-core"
argument_list|,
name|JERSEY_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"com.sun.jersey"
argument_list|,
literal|"jersey-server"
argument_list|,
name|JERSEY_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"com.sun.jersey"
argument_list|,
literal|"jersey-client"
argument_list|,
name|JERSEY_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"com.sun.jersey.test.osgi.http-service-tests"
argument_list|,
literal|"http-service-test-bundle"
argument_list|,
name|JERSEY_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.servicemix.bundles"
argument_list|,
literal|"org.apache.servicemix.bundles.jetty-bundle"
argument_list|,
literal|"6.1.14_2"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.ops4j.pax.web"
argument_list|,
literal|"pax-web-api"
argument_list|,
name|PAX_WEB_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.ops4j.pax.web"
argument_list|,
literal|"pax-web-spi"
argument_list|,
name|PAX_WEB_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.ops4j.pax.web"
argument_list|,
literal|"pax-web-runtime"
argument_list|,
name|PAX_WEB_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.ops4j.pax.web"
argument_list|,
literal|"pax-web-jetty"
argument_list|,
name|PAX_WEB_VERSION
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"commons-io"
argument_list|,
literal|"commons-io"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.clerezza"
argument_list|,
literal|"org.apache.clerezza.rdf.core"
argument_list|,
literal|"0.12-incubating-SNAPSHOT"
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * You will get a list of bundles installed by default      * plus your testcase, wrapped into a bundle called pax-exam-probe      */
annotation|@
name|Test
specifier|public
name|void
name|testInstalledBundles
parameter_list|()
block|{
for|for
control|(
name|Bundle
name|b
range|:
name|bundleContext
operator|.
name|getBundles
argument_list|()
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Bundle "
operator|+
name|b
operator|.
name|getBundleId
argument_list|()
operator|+
literal|" : "
operator|+
name|b
operator|.
name|getSymbolicName
argument_list|()
operator|+
literal|" state: "
operator|+
name|b
operator|.
name|getState
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|32
argument_list|,
name|b
operator|.
name|getState
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testHttpService
parameter_list|()
block|{
name|ServiceReference
name|ref
init|=
name|bundleContext
operator|.
name|getServiceReference
argument_list|(
name|HttpService
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|ref
argument_list|)
expr_stmt|;
name|HttpService
name|httpService
init|=
operator|(
name|HttpService
operator|)
name|bundleContext
operator|.
name|getService
argument_list|(
name|ref
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|httpService
argument_list|)
expr_stmt|;
block|}
comment|//@Test
specifier|public
name|void
name|testStandaloneService
parameter_list|()
block|{
comment|// TODO: doesn't work, service reference is not found.
name|ServiceReference
name|ref
init|=
name|bundleContext
operator|.
name|getServiceReference
argument_list|(
name|JettyServer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|JettyServer
name|svcObj
init|=
operator|(
name|JettyServer
operator|)
name|bundleContext
operator|.
name|getService
argument_list|(
name|ref
argument_list|)
decl_stmt|;
comment|// Functional tests should go there...
block|}
block|}
end_class

end_unit

