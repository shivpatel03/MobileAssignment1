# Layouts
- There are three main layouts: the `StartPage`, the `MainPage`, and a `ResultingPage`

## StartPage 
- This page simnply includes a `start` button that, when clicked, creates an intent to the MainPage where the user can enter their information for the calculation
## Main Page
- This page includes all the fields to fill out that are necessary to calculate the EMI (principle amount, interest rates, amortization period, etc.)
- As well as a `calculate` button that, when clicked, creates an intent to the ResultingPage where all the final values are viewable
## Resulting Page
- This page simply shows the calculated values, based on the inputs from the MainPage. It also includes a `recalculate` button that, when clicked, creates an intent to the MainPage, to calculate again

# Intents 
- There are three main intents in this application:
- Clicking the `Start` button redirects to the MainPage (onClick)
- Clicking the `Calculate` button directs to the ResultingPage (onClick)
- Clicking the `Recalculate` button directs back to the MainPage (onClick)
