# SkVault
SkVault allows Vault to hook into your economy script so other plugins (including Skript) can use your custom economy.

### Documentation
- [Economy Properties](#economy-properties)
- [Automatic Linking](#automatic-linking)
- [Vault Request Events](#vault-request-events)
- [UnsupportedOperationException](#unsupportedoperationexception)
- [Syntax](#syntax)
  - [Events](#events)
  - [Effects](#effects)
  - [Expressions](#expressions)

## Economy Properties
Economy properties define information about your custom economy:
```
#economy-name: MyEconomy
#currency-name: dollar
#currency-name-plural: dollars
#currency-format: $%number%
#decimal-places: 2
#auto-link-var: {balances::%player's uuid%}
```
They are defined in comments because some plugins (including Skript) request these values before scripts load. For this reason, you must restart your server to update economy properties, reloading the script will not update them. The properties can be in any order, anywhere in your script. You do not have to define all of the economy properties, but if Vault requests a property that you have not defined then an [UnsupportedOperationException](#unsupportedoperationexception) will be thrown.

## Automatic Linking
Enabling automatic linking will make SkVault automatically handle economy requests from Vault. To enable automatic linking set the `auto-link-var` economy property to the variable that you want to hold the player balances.

Example:
```
#auto-link-var: {balances::%player's uuid%}
```

Requests from Vault will now be handled automatically using the variable you set. This is adequate for most users but if you would like full control over requests from Vault use [Vault request events](#vault-request-events).

## Vault Request Events
Vault request events are for users who want to be able to fully control what happens when Vault requests something from their economy. The format of a Vault request event is:
```
on vault <something> request:
    return <something> to the request
```

In the syntax list, the request events will have `(Return: number/boolean/string/economy response)` in their title which tells you the type of value that should be returned. Some events expect a simple value such as a number and others expect you to return an economy response. An economy response gives Vault information about the actions you took upon receiving a request. 

Example 1:
```
on vault player balance request:
    return {balances::%event-offlineplayer's uuid%}
```

Example 2:
```
on vault balance deposit request:
    add event-number to {balances::%event-offlineplayer's uuid%}
    return economy response with amount modified event-number, new balance {balances::%event-offlineplayer's uuid%}, response type success, and error message "none"
```

The complete list of Vault request event is available in the [syntax](#syntax) section.

## UnsupportedOperationException
If Vault requests an economy property that you have not defined or if you did not return a value in a request event then you will see a `UnsupportedOperationException` stacktrace in console. To fix these errors simply define the requested economy property or implement the specified request event.

Example:
```
...
[21:01:41 ERROR]: #!#! Stack trace:
[21:01:41 ERROR]: #!#! java.lang.UnsupportedOperationException: BalanceRequestEvent
[21:01:41 ERROR]: #!#!     at us.donut.skvault.events.VaultRequestEvent.getReturnValue(VaultRequestEvent.java:30)
[21:01:41 ERROR]: #!#!     at us.donut.skvault.CustomEconomy.getBalance(CustomEconomy.java:127)
[21:01:41 ERROR]: #!#!     at us.donut.skvault.CustomEconomy.getBalance(CustomEconomy.java:200)
[21:01:41 ERROR]: #!#!     at ch.njol.skript.hooks.economy.expressions.ExprBalance.convert(ExprBalance.java:57)
...
```
In this case, Vault tried to get the balance of a player but automatic linking was not enabled and the balance request event was not implemented so an `UnsupportedOperationException` was thrown.

## Syntax
- [Events](#events)
- [Effects](#effects)
- [Expressions](#expressions)
 
## Events
 
### On Vault Request - Balance Deposit (Return: economy response)
Called when Vault requests to deposit currency into a player's balance
 
```java
on vault deposit request:
    add event-number to {balances::%event-offlineplayer's uuid%}
    return economy response with amount modified event-number, new balance {balances::%event-offlineplayer's uuid%}, response type success, and error message "none"
```
<details><summary>Syntaxes</summary><p>
 
```java
[on] [vault] [player] [bal[ance]] deposit request
```
 
</p></details>
<details><summary>Event values</summary><p>
 
```java
event-number
event-offlineplayer
```
 
</p></details>
<p>
</p>
<table>
  <th><div title="Since which version it was added.">Since</div></th>
  <td>2.0</td>
  <th><div title="It means if you can cancel this event from happening or not."><a href ="http://bensku.github.io/Skript/effects.html#EffCancelEvent">Cancellable</a></div></th>
  <td>false</td>
</table>
 
---
 
### On Vault Request - Balance Withdraw (Return: economy response)
Called when Vault requests to withdraw currency from a player's balance
 
```java
on vault withdraw request:
    remove event-number from {balances::%event-offlineplayer's uuid%}
    return economy response with amount modified event-number, new balance {balances::%event-offlineplayer's uuid%}, response type success, and error message "none"
```
<details><summary>Syntaxes</summary><p>
 
```java
[on] [vault] [player] [bal[ance]] withdraw request
```
 
</p></details>
<details><summary>Event values</summary><p>
 
```java
event-number
event-offlineplayer
```
 
</p></details>
<p>
</p>
<table>
  <th><div title="Since which version it was added.">Since</div></th>
  <td>2.0</td>
  <th><div title="It means if you can cancel this event from happening or not."><a href ="http://bensku.github.io/Skript/effects.html#EffCancelEvent">Cancellable</a></div></th>
  <td>false</td>
</table>
 
---
 
### On Vault Request - Check Balance (Return: boolean)
Called when Vault requests to know if a player's balance is greater than a certain amount
 
```java
on vault check player balance request:
    if {balances::%event-offlineplayer's uuid%} is greater than event-number:
        return true
    else:
        return false
```
<details><summary>Syntaxes</summary><p>
 
```java
[on] [vault] check [player] bal[ance] request
```
 
</p></details>
<details><summary>Event values</summary><p>
 
```java
event-number
event-offlineplayer
```
 
</p></details>
<p>
</p>
<table>
  <th><div title="Since which version it was added.">Since</div></th>
  <td>2.0</td>
  <th><div title="It means if you can cancel this event from happening or not."><a href ="http://bensku.github.io/Skript/effects.html#EffCancelEvent">Cancellable</a></div></th>
  <td>false</td>
</table>
 
---
 
### On Vault Request - Create Account (Return: boolean)
Called when Vault requests a player economy account to be created
 
```java
on vault create player account request:
    set {balances::%event-offlineplayer's uuid%} to 0
    return true
```
<details><summary>Syntaxes</summary><p>
 
```java
[on] [vault] create [player] [eco[nomy]] account request
```
 
</p></details>
<details><summary>Event values</summary><p>
 
```java
event-offlineplayer
```
 
</p></details>
<p>
</p>
<table>
  <th><div title="Since which version it was added.">Since</div></th>
  <td>2.0</td>
  <th><div title="It means if you can cancel this event from happening or not."><a href ="http://bensku.github.io/Skript/effects.html#EffCancelEvent">Cancellable</a></div></th>
  <td>false</td>
</table>
 
---
 
### On Vault Request - Has Account (Return: boolean)
Called when Vault requests to know if a player has an economy account
 
```java
on vault player has account request:
    if {balances::%event-offlineplayer's uuid%} is set:
        return true
    else:
        return false
```
<details><summary>Syntaxes</summary><p>
 
```java
[on] [vault] [player] has [eco[nomy]] account request
```
 
</p></details>
<details><summary>Event values</summary><p>
 
```java
event-offlineplayer
```
 
</p></details>
<p>
</p>
<table>
  <th><div title="Since which version it was added.">Since</div></th>
  <td>2.0</td>
  <th><div title="It means if you can cancel this event from happening or not."><a href ="http://bensku.github.io/Skript/effects.html#EffCancelEvent">Cancellable</a></div></th>
  <td>false</td>
</table>
 
---
 
### On Vault Request - Player Balance (Return: number)
Called when Vault requests a player's balance
 
```java
on vault player balance request:
    return {balances::%event-offlineplayer's uuid%}
```
<details><summary>Syntaxes</summary><p>
 
```java
[on] [vault] [player] bal[ance] [value] request
```
 
</p></details>
<details><summary>Event values</summary><p>
 
```java
event-offlineplayer
```
 
</p></details>
<p>
</p>
<table>
  <th><div title="Since which version it was added.">Since</div></th>
  <td>2.0</td>
  <th><div title="It means if you can cancel this event from happening or not."><a href ="http://bensku.github.io/Skript/effects.html#EffCancelEvent">Cancellable</a></div></th>
  <td>false</td>
</table>

## Effects
 
### Vault Request - Return Value
Returns a value to a request from Vault
 
**No examples available yet.**
<details><summary>Syntaxes</summary><p>
 
```java
return %object% [to [the] [vault] request]
```
 
</p></details>
<p>
</p>
<table>
  <th><div title="Since which version it was added.">Since</div></th>
  <td>2.0</td>
</table>

## Expressions
 
### Economy Response
No description available yet.
 
**No examples available yet.**
<details><summary>Syntaxes</summary><p>
 
```java
[a] [new] eco[nomy] response [with] [amount [modified]] %number%, [new] [bal[ance]] %number%, [[response] type] (success|failure|not implemented), [and] [error [message]] %string% [to [the] [vault] request]
```
 
</p></details>
<p>
</p>
<table>
  <th><div title="Since which version it was added.">Since</div></th>
  <td>1.0</td>
  <th><div title="What type it returns">Return type</div</th>
  <td>Object</td>
  <th><div title="The possible modifiers that this expression accepts."><a href="http://bensku.github.io/Skript/effects.html#EffChange">Changers</a></div></th>
  <td>none</td>
</table>
