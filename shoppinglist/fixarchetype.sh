#! /bin/sh

echo "In Script"

mv target/generated-sources/archetype/src/main/resources/META-INF/maven/archetype-metadata.xml target/generated-sources/archetype/src/main/resources/META-INF/maven/archetype-metadata.generated.xml
cat target/generated-sources/archetype/src/main/resources/META-INF/maven/archetype-metadata.generated.xml | \
sed \
	-e 's;\([ \t]*\)<include>.project</include>;\1<include>.project</include>\n\1<include>pom.xml</include>;' \
	-e 's;                <include>Dockerfile</include>;              </includes>\n            </fileSet>\n            <fileSet filtered="true" encoding="UTF-8">\n              <directory></directory>\n              <includes>\n                <include>Dockerfile</include>;' \
	> target/generated-sources/archetype/src/main/resources/META-INF/maven/archetype-metadata.xml

mv target/generated-sources/archetype/src/main/resources/archetype-resources/pom.xml target/generated-sources/archetype/src/main/resources/archetype-resources/pom.generated.xml
cat target/generated-sources/archetype/src/main/resources/archetype-resources/pom.generated.xml | \
sed '
	
	s;__rootArtifactId__;${rootArtifactId};g

	s;<groupId>.*</groupId>;<groupId>${groupId}</groupId>;

	s;<artifactId>${artifactId}</artifactId>;<artifactId>${rootArtifactId}-solution</artifactId>;

' > target/generated-sources/archetype/src/main/resources/archetype-resources/pom.xml

mv target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-api/pom.xml target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-api/pom.generated.xml
cat target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-api/pom.generated.xml | \
sed '
	/\([ \t]*\)<parent>/{:a;N;/<\/parent>/!ba;N;s;.*\n;\
	<parent>\
		<groupId>org.awesley.microservice</groupId>\
		<artifactId>microservice-api-base</artifactId>\
		<version>1.0</version>\
		<relativePath></relativePath>\
	</parent>\n;\
	}

	s;__rootArtifactId__;${rootArtifactId};g

	s;<artifactId>${artifactId}</artifactId>;<artifactId>${rootArtifactId}-api</artifactId>;

' > target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-api/pom.xml

mv target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ck/pom.xml target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ck/pom.generated.xml
cat target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ck/pom.generated.xml | \
sed '
	/\([ \t]*\)<parent>/{:a;N;/<\/parent>/!ba;N;s;.*\n;\
	<parent>\
		<groupId>org.awesley.microservice</groupId>\
		<artifactId>microservice-ck-base</artifactId>\
		<version>1.0</version>\
		<relativePath></relativePath>\
	</parent>\n;\
	}

	s;__rootArtifactId__;${rootArtifactId};g

	s;<artifactId>${artifactId}</artifactId>;<artifactId>${rootArtifactId}-ck</artifactId>;

' > target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ck/pom.xml

mv target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/pom.xml target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/pom.generated.xml
cat target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/pom.generated.xml | \
sed '
	/\([ \t]*\)<parent>/{:a;N;/<\/parent>/!ba;N;s;.*\n;\
	<parent>\
		<groupId>${groupId}</groupId>\
		<artifactId>${rootArtifactId}-solution</artifactId>\
		<version>${version}</version>\
		<relativePath>../</relativePath>\
	</parent>\n;\
	}

	s;__rootArtifactId__;${rootArtifactId};g

	s;<artifactId>${artifactId}</artifactId>;<artifactId>${rootArtifactId}-ms</artifactId>;

' > target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/pom.xml

mv target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/pom.xml target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/pom.generated.xml
cat target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/pom.generated.xml | \
sed '
	/\([ \t]*\)<parent>/{:a;N;/<\/parent>/!ba;N;s;.*\n;\
	<parent>\
		<groupId>org.awesley.microservice</groupId>\
		<artifactId>microservice-application-base</artifactId>\
		<version>1.0</version>\
		<relativePath></relativePath>\
	</parent>\n;\
	}

	s;__rootArtifactId__;${rootArtifactId};g

	s;<artifactId>${artifactId}</artifactId>;<artifactId>${rootArtifactId}-application</artifactId>;
	s;<docker.image.name>dockerimagename</docker.image.name>;<docker.image.name>${dockerimagename}</docker.image.name>;

' > target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/pom.xml

mv target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/src/main/resources/application.properties target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/src/main/resources/application.generated.properties
cat target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/src/main/resources/application.generated.properties | \
sed '
	s;__rootArtifactId__;${rootArtifactId};g
	
' > target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/src/main/resources/application.properties

mv target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-gateway/pom.xml target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-gateway/pom.generated.xml
cat target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-gateway/pom.generated.xml | \
sed '
	/\([ \t]*\)<parent>/{:a;N;/<\/parent>/!ba;N;s;.*\n;\
	<parent>\
		<groupId>org.awesley.microservice</groupId>\
		<artifactId>microservice-gateway-base</artifactId>\
		<version>1.0</version>\
		<relativePath></relativePath>\
	</parent>\n;\
	}

	s;__rootArtifactId__;${rootArtifactId};g

	s;<artifactId>${artifactId}</artifactId>;<artifactId>${rootArtifactId}-gateway</artifactId>;

' > target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-gateway/pom.xml

mv target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-persistence/pom.xml target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-persistence/pom.generated.xml
cat target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-persistence/pom.generated.xml | \
sed '
	/\([ \t]*\)<parent>/{:a;N;/<\/parent>/!ba;N;s;.*\n;\
	<parent>\
		<groupId>org.awesley.microservice</groupId>\
		<artifactId>microservice-persistence-base</artifactId>\
		<version>1.0</version>\
		<relativePath></relativePath>\
	</parent>\n;\
	}

	s;__rootArtifactId__;${rootArtifactId};g

	s;<artifactId>${artifactId}</artifactId>;<artifactId>${rootArtifactId}-persistence</artifactId>;

' > target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-persistence/pom.xml

mv target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-resource/pom.xml target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-resource/pom.generated.xml
cat target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-resource/pom.generated.xml | \
sed '
	/\([ \t]*\)<parent>/{:a;N;/<\/parent>/!ba;N;s;.*\n;\
	<parent>\
		<groupId>org.awesley.microservice</groupId>\
		<artifactId>microservice-resource-base</artifactId>\
		<version>1.0</version>\
		<relativePath></relativePath>\
	</parent>\n;\
	}

	s;__rootArtifactId__;${rootArtifactId};g

	s;<artifactId>${artifactId}</artifactId>;<artifactId>${rootArtifactId}-resource</artifactId>;

' > target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-resource/pom.xml

mv target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-service/pom.xml target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-service/pom.generated.xml
cat target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-service/pom.generated.xml | \
sed '
	/\([ \t]*\)<parent>/{:a;N;/<\/parent>/!ba;N;s;.*\n;\
	<parent>\
		<groupId>org.awesley.microservice</groupId>\
		<artifactId>microservice-service-base</artifactId>\
		<version>1.0</version>\
		<relativePath></relativePath>\
	</parent>\n;\
	}

	s;__rootArtifactId__;${rootArtifactId};g

	s;<artifactId>${artifactId}</artifactId>;<artifactId>${rootArtifactId}-service</artifactId>;

' > target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-service/pom.xml

mv target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/Dockerfile target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/Dockerfile.generated
cat target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/Dockerfile.generated | \
sed 's;__rootArtifactId__;${rootArtifactId};' > target/generated-sources/archetype/src/main/resources/archetype-resources/__rootArtifactId__-ms/__rootArtifactId__-application/Dockerfile
