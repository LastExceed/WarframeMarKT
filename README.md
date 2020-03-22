# WarframeMarKT
Kotlin wrapper for `api.warframe.market/v1/`

## How to use

Every api endpoint has a corresponding function. It's name is the request type, and it's location represents the url.

Example:
```
GET api.warframe.market/v1/tools/ducats
```
becomes
```kotlin
WarframeMarket.v1.tools.ducats.get()
```
Some endpoints have dynamic names. These are implemented using functions whose names are in ALL CAPS for clarity.

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
- `WarframeMarket.v1.profile.orders.ORDER(order_id).close()` instead of `WarframeMarket.v1.profile.orders.ORDER(order_id).close().update()

These changes were made because the code would otherwise be very confusing to someone who doesn't know the implementation details of the API.

The api is mostly undocumented, the documentations provided by warframe.market or WFDC are incomplete, of poor quality and outdated. Many endpoints had to be analyzed using browser debugging tools. For this reason there is a good chance that this wrapper isn't complete either. If you find a missing endpoint, please open an issue in this repository.
