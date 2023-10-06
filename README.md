# Configuração de compilação no projeto destino

### Acrescentar esse plugin atualizando a versão do CommonLibUtils


            <plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>aspectj-maven-plugin</artifactId>
				<version>${aspectj-plugin.version}</version>
				<configuration>
					<complianceLevel>${maven.compiler.target}</complianceLevel>
					<source>${maven.compiler.target}</source>
					<target>${maven.compiler.target}</target>
					<showWeaveInfo>true</showWeaveInfo>
					<verbose>true</verbose>
					<Xlint>ignore</Xlint>
					<encoding>${project.build.sourceEncoding}</encoding>
					<forceAjcCompile>true</forceAjcCompile>
					<sources/><!-- this is important!-->
<!--				<weaveDependencies>-->
<!--					<weaveDependency>-->
					<aspectLibraries>
						<aspectLibrary>
							<groupId>br.com.personal</groupId>
							<artifactId>common-lib-utils</artifactId>
						</aspectLibrary>
					</aspectLibraries>
<!--					</weaveDependency>-->
<!--				</weaveDependencies>-->
					<weaveDirectories>
						<weaveDirectory>${project.build.directory}/classes</weaveDirectory>
					</weaveDirectories>
				</configuration>
				<executions>
					<execution>
						<phase>process-classes</phase>
						<goals>
							<goal>compile</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
