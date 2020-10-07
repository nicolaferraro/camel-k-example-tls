# TLS config with Camel K

This example shows how to configure TLS parameters on a Camel K integration.

## Requirements

- A Kubernetes/OpenShift cluster
- Camel K installed in the current namespace
- The kubectl and the kamel CLI installed on the host

## Running

The code tries to fetch data from https://self-signed.badssl.com/, which has a self-signed certificate.
Trying to run the integration without a custom trust store causes an **x509 error for invalid certificate chain**.

This repo contains a JKS trust store (made with [Portecle](https://sourceforge.net/projects/portecle/)) that trusts
the remote self-signed certificate.

Once connected to a Kubernetes namespace, you can create a secret (named `trust`) from the trust store using the command:

```
kubectl create secret generic trust --from-file trust.jks
```


The integration is configured (using modeline comments) to include the secret named `trust` and the properties in the file `tls.properties`, which configure the SSLContextParameters for the Camel `http` component.

Running the integration, should load the trust store and print the page content without errors:

```
kamel run Tls.java --dev
```

Example output:

```
[1] 2020-10-07 08:53:08.009 INFO  [main] AbstractCamelContext - Apache Camel 3.4.0 (camel-k) started in 0.092 seconds
[1] 2020-10-07 08:53:09.881 INFO  [Camel (camel-k) thread #0 - timer://java] info - Exchange[ExchangePattern: InOnly, BodyType: String, Body: <!DOCTYPE html><html><head>  <meta charset="utf-8">  <meta name="viewport" content="width=device-width, initial-scale=1">  <link rel="shortcut icon" href="/icons/favicon-red.ico"/>  <link rel="apple-touch-icon" href="/icons/icon-red.png"/>  <title>self-signed.badssl.com</title>  <link rel="stylesheet" href="/style.css">  <style>body { background: red; }</style></head><body><div id="content">  <h1 style="font-size: 12vw;">    self-signed.<br>badssl.com  </h1></div></body></html>]
```