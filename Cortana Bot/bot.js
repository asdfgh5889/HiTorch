// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

// bot.js is your main bot dialog entry point for handling activity types

// Import required Bot Builder
const { MessageFactory, ActivityTypes, CardFactory } = require('botbuilder');
const { LuisRecognizer } = require('botbuilder-ai');
const { BotConfiguration } = require('botframework-config');
let request = require('request-promise');
request = request.defaults({jar: true})

// LUIS service type entry as defined in the .bot file.
const LUIS_CONFIGURATION = 'hitorch';
let context_state 

const regions = {
    '6' :'Namangan',
    '7' :'Navoiy',
    '15': 'Other',
    '12': 'Karakalpakistan',
    '11': 'Tashkent region',
    '14': 'Samarkand',
    '13': 'Tashkent',
    '2' :'Bukhara',
    '1' :'Andijan',
    '4' :'Jizzakh',
    '3' :'Fergana',
    '5' :'Khorezm',
    '8' :'Kashkadarya',
    '10': 'Surkhandarya'
}

/**
 * Demonstrates the following concepts:
 *  Displaying a Welcome Card, using Adaptive Card technology
 *  Use LUIS to model Greetings, Help, and Cancel interactions
 *  Use a Waterfall dialog to model multi-turn conversation flow
 *  Use custom prompts to validate user input
 *  Store conversation and user state
 *  Handle conversation interruptions
 */
class BasicBot
{
    /**
     * Constructs the three pieces necessary for this bot to operate:
     * 1. StatePropertyAccessor for conversation state
     * 2. StatePropertyAccess for user state
     * 3. LUIS client
     * 4. DialogSet to handle our GreetingDialog
     *
     * @param {ConversationState} conversationState property accessor
     * @param {UserState} userState property accessor
     * @param {BotConfiguration} botConfig contents of the .bot file
     */
    constructor(botConfig)
    {
        if (!botConfig) throw new Error('Missing parameter.  botConfig is required');

        // Add the LUIS recognizer.
        const luisConfig = botConfig.findServiceByNameOrId(LUIS_CONFIGURATION);
        if (!luisConfig || !luisConfig.appId) throw new Error('Missing LUIS configuration. Please follow README.MD to create required LUIS applications.\n\n');
        const luisEndpoint = luisConfig.region && luisConfig.region.indexOf('https://') === 0 ? luisConfig.region : luisConfig.getEndpoint();
        this.luisRecognizer = new LuisRecognizer({
            applicationId: luisConfig.appId,
            endpoint: luisEndpoint,
            // CAUTION: Its better to assign and use a subscription key instead of authoring key here.
            endpointKey: luisConfig.authoringKey
        });
    }

    /**
     * Driver code that does one of the following:
     * 1. Display a welcome card upon receiving ConversationUpdate activity
     * 2. Use LUIS to recognize intents for incoming user message
     * 3. Start a greeting dialog
     * 4. Optionally handle Cancel or Help interruptions
     *
     * @param {Context} context turn context from the adapter
     */
    async onTurn (context)
    {
        context_state = context
        // Handle Message activity type, which is the main activity type for shown within a conversational interface
        // Message activities may contain text, speech, interactive cards, and binary or unknown attachments.
        // see https://aka.ms/about-bot-activity-message to learn more about the message and other activity types
        if (context.activity.type === ActivityTypes.Message)
        {
            // Perform a call to LUIS to retrieve results for the current activity message.
            const results = await this.luisRecognizer.recognize(context);
            const topIntent = LuisRecognizer.topIntent(results);
            if (topIntent === 'makeTripPlan')
            {
                if (results.entities['duration'] !== undefined)
                {
                    const duration = results.entities['duration'][0].match(/\d+/)[0];
                    if (duration !== undefined)
                    {
                        //await context.sendActivity(duration);

                        var options = {
                            method: 'POST',
                            uri: 'http://fizmasoft.uz/hitorch/API/login',
                            formData: {
                                email: 'mr.muhammadsher@mail.ru',
                                password: 'admin12345'
                            },
                            headers: {
                                /* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
                            },
                            json: true, // Automatically stringifies the body to JSON
                            withCredentials: true
                        };

                        await request(options)
                            .then(function (parsedBody)
                            {
                                //context.sendActivity('test two')
                                console.log(parsedBody)
                            })
                            .catch(function (err)
                            {
                                console.log(err)
                            });

                        var options2 = {
                            method: 'POST',
                            uri: 'http://fizmasoft.uz/hitorch/API/createTripPlan',
                            body: {
                                time: duration
                            },
                            json: true, // Automatically stringifies the body to JSON
                            withCredentials: true
                        };

                        await request(options2)
                            .then(function (response)
                            {
                                //let jsonObj = 
                                response.sort(function (a, b) {
                                  return b.priority - a.priority;
                                });
                                const text = `You'll start your adventure from ${regions[response[0].region_id]}'s ${response[0].name} and go through ${regions[response[1].region_id]}'s ${response[1].name} and end with ${regions[response[2].region_id]}'s ${response[2].name}. I'll send trip details to your High Torch app.`;
                                //session.say(text);
                                //context.say(text);
                                let msg = MessageFactory.text(text, text);
                                context.sendActivity(msg);
                            })
                            .catch(function (err)
                            {
                                console.log(err)
                            });
                    }
                    else 
                    {
                        await context.sendActivity("Sorry, but I can't do that yet")
                    }
                }
            }
            else 
            {
                await context.sendActivity("Sorry, but I can't do that yet")
            }
            // Based on LUIS topIntent, evaluate if we have an interruption.
        }
    }
}

module.exports.BasicBot = BasicBot;
