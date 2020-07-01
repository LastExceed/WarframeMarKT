# WarframeMarKT
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.lastexceed/WarframeMarKT/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.lastexceed/WarframeMarKT) <- click here to see how to import the latest version into your project

Kotlin wrapper for `api.warframe.market`

Available on MavenCentral: `com.github.lastexceed:WarframeMarKT:1.0.1`

## How to use

Every API endpoint has a corresponding function. It's name is the request type, and it's location represents the URL.

Example:
```
GET api.warframe.market/v1/tools/ducats
```
becomes
```kotlin
WarframeMarket.v1.tools.ducats.get()
```
Some endpoints have dynamic names. These are implemented using functions whose names are in ALL_CAPS for clarity.

Example:
```
GET api.warframe.market/v1/items/maiming_strike/orders
```
becomes
```kotlin
WarframeMarket.v1.items.ITEM("maiming_strike").orders.get()
```
There are a few exceptions to this structure:
- POST and PUT have been renamed to `.create()` and `.update()` respectively
- `WarframeMarket.v1.auth.signin()` instead of `WarframeMarket.v1.auth.signin.create()` (same for `signout` and `register`)
- `WarframeMarket.v1.auth.changePassword()` instead of `WarframeMarket.v1.settings.account.change_password.create()`
- `WarframeMarket.v1.auctions.entry.ENTRY("").close()` instead of `WarframeMarket.v1.auctions.entry.ENTRY("").close.update()`
- `WarframeMarket.v1.profile.orders.ORDER(order_id).close()` instead of `WarframeMarket.v1.profile.orders.close.ORDER(order_id).update()` (note how the arrangement changed)

These changes were made because the code would otherwise be very confusing to someone who isn't already familiar with the API and its shenanigans.

Here is a list of all endpoints this wrapper covers:
```
api.warframe.market
    /v1
        /auctions                    GET
            /create                  POST
            /entry
                /{auction_id}        GET PUT
                    /bids            GET
                    /close           PUT
            /participant             GET
            /popular                 GET
            /search                  GET
        /auth
            /signin                  POST
            /signout                 GET
            /register                POST
        /im
            /chats                   GET
                /{chat_id}           GET DELETE
            /ignore                  GET CREATE
                /{user_id}           DELETE
        /items                       GET
            /{item_url_name}         GET
                /orders              GET
                    /top             GET
                /statistics          GET
        /tools
            /ducats                  GET
        /profile                     GET
            /auctions                GET
            /customization            
                /about               PUT
                /avatar              POST
            /orders                  GET POST
                /close
                    /{order_id}      PUT
                /{order_id}          PUT DELETE
            /{username}              GET
                /achievements        GET
                /orders              GET
                /review              POST
                    /{review_id}     PUT DELETE
                /reviews             GET
                /statistics          GET
        /settings
            /accounts
                /change_password     POST
            /notifications
                /push                POST DELETE
            /verification            PATCH
```
The API documentations provided by warframe.market and WFDC are incomplete, of poor quality and outdated. Many endpoints are completely undocumented and had to be found and analyzed using browser debugging tools. For this reason there is a good chance that this list isn't exhaustive. If you find a missing endpoint, please open an issue in this repository.
