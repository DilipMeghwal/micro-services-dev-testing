# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.0-M1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.0-M1/maven-plugin/reference/html/#build-image)

### h2 db
- http://localhost:8090/h2-console/

### Open API docs
- http://localhost:8090/v3/api-docs
- http://localhost:8090/swagger-ui/index.html
- https://www.baeldung.com/spring-rest-openapi-documentation
- https://www.baeldung.com/spring-rest-docs-vs-openapi

## CI-CD

### install helm
- https://github.com/helm/helm/releases
- set path in env variables after extracting the zip
- relaunch the cmd
- type helm -h

- docker image: docker build -t tag-name .
- docker images
- docker run -p 8090:8090 image-tag-name

https://anywhere.epam.com/en/blog/how-to-dockerize-spring-boot-application

### with helm charts
* helm create countryCharts
* tree countryCharts
* image:
  repository: country-ms
* service:
  type: NodePort
  port: 8090
* minikube start --driver=docker
* macos: eval $(minikube docker-env)
* win : minikube docker-env
* docker build -t tag-name .
* helm install chart-name-any chart-folder-name
  Get the application URL by running these commands:
  export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=countrycharts,app.kubernetes.io/instance=country-chart" -o jsonpath="{.items[0].metadata.name}")
  export CONTAINER_PORT=$(kubectl get pod --namespace default $POD_NAME -o jsonpath="{.spec.containers[0].ports[0].containerPort}")
  echo "Visit http://127.0.0.1:8080 to use your application"
  kubectl --namespace default port-forward $POD_NAME 8080:$CONTAINER_PORT
* kubectl get all -A
* minikube service country-ms-country-charts --url
* minikube addons enable ingress
* values file :
  service:
  type: ClusterIP
  port: 8090

  ingress:
  enabled: true
  className: ""
  annotations: {}
  # kubernetes.io/ingress.class: nginx
  # kubernetes.io/tls-acme: "true"
  hosts:
    - host: countries.com
      paths:
        - path: /
          pathType: Prefix


## Error Faced
- failed to read dockerfile: open Dockerfile: no such file or directory
    Sol : rename docker file to Dockerfile
- Unable to resolve the current Docker CLI context "default"
    sol : docker context use default
- chart file:
  version: 0.1.1
- helm upgrade country-chart countryCharts


helm template ./country-chart
minikube docker-env

## working on windows
* minikube start --driver=docker
* minikube addons enable ingress
* minikube docker-env
* minikube dashboard
* docker build -t country-ms:v0.0.1 .
* docker push dilipmeghwal/country-ms:v0.0.1
* helm install --debug country-ms country-charts
* helm ls
* helm uninstall country-ms
* minikube service country-ms-country-charts --url