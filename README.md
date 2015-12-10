# cqREST - RESTful CQRS

cqREST aims to be a protocol supporting CQRS over HTTP in conformance with the REST architectural style.

The JAX-RS based implementation should serve as a very simple illustration of the protocol.

## Background information

For an introduction to REST, please refer to [Roy Fielding's dissertation](https://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm), especially [section 5](https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm).

For an introduction to CQRS, please refer to [Martin Fowler's introduction](http://martinfowler.com/bliki/CQRS.html) or to this [MSDN article](https://msdn.microsoft.com/en-us/library/jj591573.aspx).

## Disclaimer

THIS IS WORK IN PROGRESS!

If you think this is to SOAPy, don't use it and don't bother me.


# cqREST protocol

This is a very early sketch of how I think a protocol supporting RESTful CQRS (especially via HTTP) should work.

## Uniform interface

As defined by the REST architectural style, cqREST supports a [uniform interface](https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm#sec_5_1_5) by four interface constraints: identification of resources, manipulation of resources through representations, self-descriptive messages and hypermedia as the engine of application state.

cqREST heavily leverages the [HTTP protocol](https://tools.ietf.org/html/rfc7230) which it extends and constraints at the same time. 

### Identification of resources

Resources are identified by Uniform Resource Identifiers (URIs) in accordance to the [HTTP specification, RFC 7231, section 2](https://tools.ietf.org/html/rfc7231#section-2).

### Manipulation of resources (Commands)

To support the manipulation of resources, cqREST defines a new HTTP method, namely COMMAND.
The COMMAND method requests that the target resource processes the representation enclosed in the request according to command semantics defined by CQRS. The intent of the command must be part of the representation.

Depending on the result of processing the request (i.e. the command) the origin server must choose an appropriate status code with accordance to the following rules:
* If request processing succeeded, the status must be 202 (Accepted).
* If request processing failed, the status code should be one of 4xx or 5xx (client or server error).

Afore mentioned status code semantics must conform to the HTTP specification.

A response to a COMMAND request must not carry a payload body. However, additional metadata of the outcome of the request can be transferred to the client via HTTP headers.

Clients which do not support the cqREST protocol may use HTTP POST method as a fallback, but must adhere to constraints and semantics defined above.

### Self-descriptive messages

tbd

### Hypermedia

Since a response to a COMMAND request must not carry a payload, hypermedia support is only available via a response to a QUERY request.
cqREST defines the QUERY method as a synonym/alias for HTTP GET method. Therefore, hypermedia is supported in the same manner as with every RESTful system which uses the HTTP protocol. 
Clients which do not support the cqREST protocol may use the GET method as a fallback.

Moreover, clients may send HTTP OPTIONS requests to request communication options available for the targeted resource. A response to an OPTIONS request must carry a payload that indicates which commands may be executed against the target resource. This may be a static set of commands, but should dynamically adapt to the current state of the resource.  

## Additional constraints

### Method definitions
HTTP methods not defined above must not be used.

### Common method properties

* COMMAND method is neither safe, nor idempotent, nor cacheable.
* QUERY method is safe, idempotent and may be cacheable if indicated by response headers.
* OPTIONS method is safe, idempotent and may be cacheable if indicated by response headers. 

For details on method properties and their semantics see [HTTP specification, RFC 7231, section 4.2](https://tools.ietf.org/html/rfc7231#section-4.2).