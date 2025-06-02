<h1>Technical Document of Joules App</h1>
<table >
    <thead>
        <tr><th>Tech Stack</th><th>Description</th></tr>
    </thead>
    <tbody><tr><td>KMP</td><td>Frontend framework for building user interfaces with type safety.</td></tr><tr><td>Gemini AI API</td><td>Backend service for AI-powered features.</td></tr><tr><td>Firebase Database</td><td>NoSQL cloud database for storing application data.</td></tr><tr><td>Firebase Authentication</td><td>Authentication service for user sign-in and management.</td></tr></tbody>
</table>

<h2>Screens</h2>

### Login Screen
It has a simple google sign in button and a logo. it uses firebase auth for signing in through ios and android native code and receives callback 
in kmp. <br>
 <img src="https://github.com/user-attachments/assets/c78b9cc2-40fd-4242-bff8-69977ee53572" width="270" height="480" />
 <br>
 
 ### Onboarding Screen
 In this screen we ask the user some basic questions like bmi,gender,height,intention,target weight,time to complete the target etc to get a target calories per day. from the inputs we create a prompt and ask the AI to give us a target. please find the prompt and the response below which can be subjected to more finetuning. 
 <br>
 example request:
 
 ```curl
 curl "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" \
-H 'Content-Type: application/json' \
-X POST \
-d '{
"contents": [
{
"parts": [
{
"text": "I am a male weighing around 80kgs and a height of 175cm I wish to reduce my weight to 75kgs in 2years.Calculate the calorie intake target I must have with limits in each nutrient and provide me the result in a concise json format without any other text "
}
]
}
]
}'
 ```
 
 Output
 ```
 {
  "candidates": [
    {
      "content": {
        "parts": [
          {
            "text": "```json\n{\n  \"goal\": \"Weight Loss\",\n  \"targetWeight\": 75,\n  \"timeframeYears\": 2,\n  \"currentWeight\": 80,\n  \"heightCm\": 175,\n  \"estimatedDailyCalorieDeficit\": 70,\n  \"estimatedDailyCalorieIntake\": 2450,\n  \"macroNutrientTargets\": {\n    \"protein\": {\n      \"grams\": 120,\n      \"calories\": 480,\n      \"percentage\": 20\n    },\n    \"fat\": {\n      \"grams\": 68,\n      \"calories\": 612,\n      \"percentage\": 25\n    },\n    \"carbohydrates\": {\n      \"grams\": 339,\n      \"calories\": 1358,\n      \"percentage\": 55\n    }\n  },\n  \"notes\": \"These are estimates. Individual needs may vary based on activity level, metabolism, and other factors. Consult a healthcare professional or registered dietitian for personalized advice.\"\n}\n```"
          }
        ],
        "role": "model"
      },
      "finishReason": "STOP",
      "avgLogprobs": -0.10532998459541845
    }
  ],
  "usageMetadata": {
    "promptTokenCount": 63,
    "candidatesTokenCount": 247,
    "totalTokenCount": 310,
    "promptTokensDetails": [
      {
        "modality": "TEXT",
        "tokenCount": 63
      }
    ],
    "candidatesTokensDetails": [
      {
        "modality": "TEXT",
        "tokenCount": 247
      }
    ]
  },
  "modelVersion": "gemini-2.0-flash",
  "responseId": "1TE9aPbcL4iT7dcPhZj1yQY"
}
 ```
 
 ### Dashboard
 This Screen has today's chart of calorie intake split by the macronutrients . we also have a target of each macro nutrient as well as the total calorie intake . we also show a weekly timeline chart of the targets and accomplished. clicking on a chart will lead to calorie intake in each meal.<br>
 <em>The below image is just an example of how it may look &nbsp;[credit](https://medium.com/@sonalisrabanika10/ui-ux-case-study-designing-a-calorie-tracker-app-e6e4788f7ec2)</em>
<img src="https://miro.medium.com/v2/resize:fit:720/format:webp/1*Ya_o-03s2zKEeyJi9UDCdQ.png" />
<br>

### Add meals screen
In this screen we make the user choose the meal type if its breakfast,lunch,evening snacks or dinner and take him to a screen where he searches for a dish and gets the calorie distribution. User also adds the quantity they had. <br>
 <em>The below image is just an example of how it may look &nbsp;[credit](https://medium.com/@sonalisrabanika10/ui-ux-case-study-designing-a-calorie-tracker-app-e6e4788f7ec2)</em>
 
![](https://miro.medium.com/v2/resize:fit:720/format:webp/1*3Egy-zrnmSuRdzwkNLGVdA.png)

Request for searching a dish 
```curl
curl "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" \
-H 'Content-Type: application/json' \
-X POST \
-d '{
"contents": [
{
"parts": [
{
"text": "give me a list of dishes,beverages and other types of food whose name contain letters tea .give me the names in an array and nothing else. "
}
]
}
]
}'
```
response:

```
{
  "candidates": [
    {
      "content": {
        "parts": [
          {
            "text": "```\n[\n\"Steak\",\n\"Sweet tea\",\n\"Tea\",\n\"Oatmeal\",\n\"Pate\",\n\"Wheat bread\",\n\"Chocolate\",\n\"Cote de boeuf steak\",\n\"Pea soup\",\n\"Mate\",\n\"Pita bread\",\n\"Meatloaf\"\n]\n```\n"
          }
        ],
        "role": "model"
      },
      "finishReason": "STOP",
      "avgLogprobs": -0.44406697370003961
    }
  ],
  "usageMetadata": {
    "promptTokenCount": 32,
    "candidatesTokenCount": 69,
    "totalTokenCount": 101,
    "promptTokensDetails": [
      {
        "modality": "TEXT",
        "tokenCount": 32
      }
    ],
    "candidatesTokensDetails": [
      {
        "modality": "TEXT",
        "tokenCount": 69
      }
    ]
  },
  "modelVersion": "gemini-2.0-flash",
  "responseId": "GTc9aP_zCuT9ld8P4bWS6Ag"
}
```
Getting calorie breakdown <br>
request:

```curl
curl "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" \
-H 'Content-Type: application/json' \
-X POST \
-d '{
"contents": [
{
"parts": [
{
"text": "I had a bowl of mutton biryani with raitha and some fries on the side.calculate the total calories i had along with the nutrient breakdown in grams as well as percentage. give me the concise response as json and nothing else"
}
]
}
]
}'
```
