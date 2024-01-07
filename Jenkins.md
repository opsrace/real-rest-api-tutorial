# Jenkins

## Build Automation

Test Code -> Build Application -> Push to Repository -> Deploy to Server -> Send notifications

docker run -p 8090:8080 -p 50000:50000 -d -v dev_jenkins_home:/var/jenkins_home  jenkins/jenkins:lts

50,000 is worker and client communication
-d run in background
dev_jenkins_home its a named volume, mounted inside container at /var/jenkins_home

## Get password
Docker exec -it containerName bash

`cat /var/jenkins_home/secrets/initialAdminPassword`

docker volume inspect dev_jenkins_home


http://localhost:8090/


Install suggested plugin



Create User
taya@abc123
doctor_nett@yahoo.com


## 2 Roles for Jenin App

1. Jenkins Administrator: DevOps or Operations
    administers and manages jenkins
    sets up jenkins cluster
    install plugins
    backup Jenkins data
    
2. Jenkins User: Developer or DevOps teams
    Create the actual jobs to run workflows


## Enter docker container and install nodejs
login as jenkins user
    docker exec -it c6d7680b3475 bash

login as root
    docker exec -u 0 -it c6d7680b3475 bash

    you are logged in as root user now

    check what distriution we are on cat /etc/issue
        so that we can find relevent documentaion of the tool for that distribution

    apt update
    apt install curl
    curl -sL https://deb.nodesource.com/setup_10.x -o nodesource_setup.sh
    # new version
    curl -sL https://deb.nodesource.com/setup_18.x -o nodesource_setup.sh
    bash nodesource_setup.sh
    apt install nodejs


    Install maven plugin in jenkins through tools

    Now in jenkins crate free style project and add 2 build step
    1 - Execute Shee
        npm --version

    2 - Invoke top-level maven targets
        Select Maven installed from tools [My Maven 3.9.6]
        Goals:
            --version

    Save and Build


    Now check cnsole output

    now setup git in this project
    https://github.com/opsrace/real-rest-api-tutorial

    add credetnails (on github secret code is used as follows)
    https://theToken@github.com/opsrace/real-rest-api-tutorial


    uptil this point we are able to checkout code
    and ran mvn clean package, now we need docker support to run docker commands

    docker stop container, as we will be moundting a host directory to support docker
    docker stop c6d7680b3475

    docker volume ls
    output:
        local     dev_jenkins_home

following will work on linux machine and mac if docker
we attached some volumes so that it is accessible

docker run -p 8090:8080 -p 50000:50000 -d \
-v dev_jenkins_home:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock \
-v $(which docker):/user/bin/docker  jenkins/jenkins:lts

## Giver permission so that mounted docker fankari can use commnds

docker exec -u 0 -it 8120c4fa8908 bash
ls -l /var/run/docker.sock
output:
    srwxr-xr-x 1 root root 0 Jan  1 18:44 /var/run/docker.sock

chmod 666 /var/run/docker.sock
    srw-rw-rw- 1 root root 0 Jan  1 18:44 /var/run/docker.sock
exit and login again with normal user

docker exec -it 8120c4fa8908 bash

Read this article: https://hackmamba.io/blog/2022/04/running-docker-in-a-jenkins-container/

apt-get update



curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian bookworm stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null
apt-get update
apt-get install docker.io

now add execute shell step
cd java-api
docker build -t api-example:1.0 .

Push to repository
docker build -t javaarchitect/api-example:java-1.0 .

#docker login -u $USER_NAME -p $PASSWORD
echo $PASSWORD | docker login -u $USER_NAME --password-stdin
docker push javaarchitect/rest-java-api:java-1.0

----
add credentials first before login above
Build Environement
    Use secret
    Bindings
        username and password (conj)
        USER_NAME
        PASSWORD
        @ Select credetntials


cd java-api
docker build -t javaarchitect/api-example:java-1.0 .
echo $PASSWORD | docker login -u $USER_NAME --password-stdin
docker push javaarchitect/api-example:java-1.0


Install nexus
wget https://download.sonatype.com/nexus/3/nexus-3.63.0-01-mac.tgz
curl -O -L https://download.sonatype.com/nexus/3/nexus-3.63.0-01-mac.tgz
explain:
    -O: User the same name as the remote name
    -L: follow redirect

tar -zxvf nexus-3.63.0-01-mac.tgz

drwxr-xr-x@ 13 optimus  staff   416B Jan  2 11:20 nexus-3.63.0-01
-rw-r--r--@  1 optimus  staff   262M Jan  2 11:19 nexus-3.63.0-01-mac.tgz
drwxr-xr-x@  3 optimus  staff    96B Jan  2 11:20 sonatype-work


nexus-3.63.0-01:
    Contains runtime and the applicaiton of nexus (binaries)
sonatype-work:
    Contains own config for Nexus and data
    * you can use this folder to backup your data
    
    ip access logs and other things
    logs of nexus app
    your uploaded files and meta data

Start service:
    should not be with root user
    create own user for this ervice
    only specific user

-- linux command
adduser nexus
vim nexus-3.63.0-01/bin/nexus.rc
set user to nexus
su - nexus
/opt/nexus-3.63.0-01/bin/nexus start


I ran container on mac-m2
# docker run -d -p 8086:8081 --name nexus sonatype/nexus3
docker run -d -p 8081:8081 --name nexus klo2k/nexus3

open port on your ec2 instance

docker exec -it containername bash
cat /opt/sonatype/sonatype-work/nexus3/admin.password
e32e7b4e-2e87-4859-99b6-8391d418c4c4
e32e7b4e-2e87-4859-99b6-8391d418c4c4

Now login with admin and above password (whatever is in your case)

Setting -> Repositories
Types
1. Proxy repository : link to external repository
    Once downloaded nexus will cache and keep it internal
    save network bandwidth and time
2. Group
3. Hosted


Create user on nexus that will enable upload
    Create role: dev-nexus-role
        priviliges
            nx-repository-view-maven2-maven-snapshot-*

    Create user dev-nexus
        assign the above role
        set password abc123 //very weak password


Now configure using credential

/users/optimus/installs/apache-maven-3.9.6/bin/mvn

pom.xml

<project>
    ....
    <distributionManagement>
		<snapshotRepository>
			<id>nexus-snapshot</id>
			<url>http://localhost:8081/repository/maven-snapshots/</url>
		</snapshotRepository>
	</distributionManagement>
</project>

~/.m2/settings.xml
/users/optimus/installs/apache-maven-3.9.6/conf/settings.xml

<settings>
    <servers>
        <server>
            <id>nexus-snapshot</id>
            <username>dev-nexus</username>
            <password>abc123</password>
        </server>
    </servers>
</settings>


mvn package
mvn deploy

----------
Now store our docker on Nexus
Nexus3
    Blob Sores:
        Create new : my-docker-blobstore
        /nexus-data/blobs/my-docker-blobstore



    Repositories-> Create Repository -> Docker(Hosted)
    Name: my-docker-repo
    select bobstore: my-docker-blobstore (created previously)

Get the URL of repository
http://localhost:8081/repository/my-docker-repo/


Create role
    name: nx-docker-role
    previliages:
        nx-repository-view-docker-docker-hosted-*

    now assign this to existing user
        nx-java-deploy-user (or somthing)

Open a separate port for docker connection
    Go to already exre


Sadi galli 92 Ayak$roo


docker volume create --name dev-nexus-data
$ 
docker run -d -p 8081:8081 --name nexus klo2k/nexus3
docker run -d -p 8081:8081 -p 8082:8082 -p 8083:8083 --name nexus-dev -v dev-nexus-data:/nexus-data klo2k/nexus3

configure every thing
now we need to configure "Realms"
"Docker Bearer Token Realm"

ok now to allow containers to access insecure port we need add this to our docker-desktop or docker config
as my jenkins is running in docker itself, and nexus is running on another docker
I will add these configureion to docker-desktop -> prefernces -> docker engine
and restart
{
  "builder": {
    "gc": {
      "defaultKeepStorage": "20GB",
      "enabled": true
    }
  },
  "experimental": false,
  "insecure-registries" : ["host.docker.internal:8082"]
}


Now I am on host machine and want to login nexus docker
docker login localhost:8082
usernmae: dev-nexus
password: abc123

now my local host machine is updated
/Users/optimus/.docker/config.json

Push image to nexus-docker repository
    Build Image:    
        docker build -t api-example:java-1.0 .
    Retag image:
        docker tag api-example:java-1.0 localhost:8082/api-example:java-1.0
    docker push localhost:8082/api-example:java-1.0


~/.dockerconfig.json


#sudo groupadd docker             # Create the docker group if it doesn't exist

Login as root
docker exec -it -u 0 be320b3bf70d bash

sudo usermod -aG docker jenkins   # Add your user to the docker group
sudo chown root:docker /var/run/docker.sock
sudo chmod 660 /var/run/docker.sock

docker exec -it be320b3bf70d bash
docker login -u dev-nexus host.docker.internal:8082

it means we can access the repo from docker container


--docker step
cd java-api
echo $NEXUS_PASSWORD | docker login -u $NEXUS_USERNAME --password-stdin host.docker.internal:8082

docker build -t api-example:java-1.0 .
docker tag api-example:java-1.0 host.docker.internal:8082/api-example:java-1.0
docker push host.docker.internal:8082/api-example:java-1.0


----------------------
Jenkins pipeline

pipeline {
    agent any
    stages {
        stage("Build") {
            steps {
                echo "Building..."
            }
        }
        stage("Test") {
            steps {
                echo "Testing..."
            }
        }
        stage("Deploy") {
            steps {
                echo "Deploying..."
            }
        }
    }
}

---
Parameters
agen any
parameters {
    string(name: 'VERSION', defaultValue:'', description: 'Here is the descriptions')
    choice(name: 'VERSIONS', choices:['1.0','2.0','3.0'])
    boolean(name: 'executeTests', defaultValue: true, descrition: 'Execute Test?')
}

---
pipeline {
    agent any
    parameters {
        string(name: 'Name', defaultValue:'', description: 'What is your name?')
        choice(name: 'VERSION', choices:['1.0','2.0','3.0'])
        booleanParam(name: 'runTests', defaultValue: true, description: 'Do you want to run Tests?')
    }
    stages {
        stage("Build") {
            steps {
                echo "Building..."
            }
        }
        stage("Test") {
            when {
                expression {
                    params.runTests
                }
            }
            steps {
                echo "Testing..."
            }
        }
        stage("Deploy") {
            steps {
                echo "Deploying..."
            }
        }
    }
}