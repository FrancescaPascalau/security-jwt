# Security - JWT

JWT, or JSON Web Token, is an open standard used to share security information between two parties — a client and a
server. Each JWT contains encoded JSON objects, including a set of claims. JWTs are signed using a cryptographic
algorithm to ensure that the claims cannot be altered after the token is issued.

## How it works

![](jwt.png)

JWTs differ from other web tokens in that they contain a set of claims. Claims are used to transmit information between
two parties. What these claims are depends on the use case at hand. For example, a claim may assert who issued the
token, how long it is valid for, or what permissions the client has been granted.

A JWT is a string made up of three parts, separated by dots (.), and serialized using base64. In the most common
serialization format, compact serialization, the JWT looks something like this: `xxxxx.yyyyy.zzzzz`.

Once decoded, you will get two JSON strings:

1. The header and the payload.
2. The signature.

### The header contains info on how the JWT is encoded.

### The body is the meat of the token (where the claims live).

### The signature provides the security.

A common way to use JWTs is as `OAuth` bearer tokens. In this example, an authorization server creates a JWT at the
request of a client and signs it so that it cannot be altered by any other party. The client will then send this JWT
with its request to a REST API. The REST API will verify that the JWT’s signature matches its payload and header to
determine that the JWT is valid. When the REST API has verified the JWT, it can use the claims to either grant or deny
the client’s request.

In simpler terms, you can think of a JWT bearer token as an identity badge to get into a secured building. The badge
comes with special permissions (the claims); that is, it may grant access to only select areas of the building. The
authorization server in this analogy is the reception desk — or the issuer of the badge. And to verify that the badge is
valid, the company logo is printed on it, similar to the signature of the JWT. If the badge holder attempts to access a
restricted area, the permissions on the badge determine whether they can access the area, similar to the claims
in a JWT.

Online generator: https://www.javainuse.com/jwtgenerator

# OAuth2

OAuth 2.0, which stands for “Open Authorization”, is a standard designed to allow a website or application to access
resources hosted by other web apps on behalf of a user. It replaced OAuth 1.0 in 2012 and is now the de facto industry
standard for online authorization. OAuth 2.0 provides consented access and restricts actions of what the client app can
perform on resources on behalf of the user, without ever sharing the user's credentials.

## Principles of OAuth2.0

OAuth 2.0 is an `authorization protocol` and NOT an authentication protocol. As such, it is designed primarily as a
means of granting access to a set of resources.

OAuth 2.0 uses Access Tokens. An Access Token is a piece of data that represents the authorization to access resources
on behalf of the end-user. OAuth 2.0 doesn’t define a specific format for Access Tokens. However **the
JSON Web Token (JWT) format is often used**. This enables token issuers to include data in the token itself. Also, for
security reasons, Access Tokens may have an expiration date.

## Roles

1. **Resource Owner**: The resource owner is the user who authorizes an application to access their account. The
   application’s access to the user’s account is limited to the scope of the authorization granted.
2. **Client**: The client is the application that wants to access the user’s account. Before it may do so, it must be
   authorized by the user, and the authorization must be validated by the API.
3. **Resource Server**: The resource server hosts the protected user accounts.
4. **Authorization Server**: The authorization server verifies the identity of the user then issues access tokens to the
   application.

## How it works

1. The Client requests authorization (authorization request) from the Authorization server, supplying the client id and
   secret to as identification; it also provides the scopes and an endpoint URI (redirect URI) to send the Access Token
   or the Authorization Code to.

2. The Authorization server authenticates the Client and verifies that the requested scopes are permitted.

3. The Resource owner interacts with the Authorization server to grant access.

4. The Authorization server redirects back to the Client with either an Authorization Code or Access Token, depending on
   the grant type, as it will be explained in the next section. A Refresh Token may also be returned.

5. With the Access Token, the Client requests access to the resource from the Resource server.