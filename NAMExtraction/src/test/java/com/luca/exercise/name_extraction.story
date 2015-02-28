Scenario: Extracting names from an HTML page stored on disk

Given an HTML page loaded from disk
When we apply the POSModel to classify parts of speech
Then we get back a list of names