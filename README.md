Java SpringBoot bot that can be useful for collecting data about the participants of some event or applicants for the position 
=======
# Java Spring Boot bot
This bot can collect data about the participants of some event or about the applicants for the position

# Getting started
Send /start command to the bot and it will suggest you to fill in the data or look through the list of available commands

![2022-07-23_14-43-27](https://user-images.githubusercontent.com/81825828/180601852-093291e5-6b9a-4190-b06d-68838d2ce0e3.png)

# Answering questions
Send /newdata in order to answer some simple questions and leave your contacts.

So, here you can see the first question, bot asks you to write your first name.

![2022-07-23_14-46-36](https://user-images.githubusercontent.com/81825828/180601924-e78b1b90-0c8e-42c9-8ced-9cf2d450cd0f.png)

As the last question, you will be asked to leave some additional info, this question is optional, and you may just send 0 to skip it.

After the last question, bot will thank you for leaving your contacts.
Your answers will be sent to the administrator's email, which is specified in the configuration file.

![2022-07-23_14-50-29](https://user-images.githubusercontent.com/81825828/180602033-0a041d69-9f66-4f03-8b1f-86f2a190818a.png)

# Validation
There are some constraints for the data you enter.

For example, the length of your first name must be between 2 and 100 symbols. Otherwise, you will see the error message:

![2022-07-23_14-59-56](https://user-images.githubusercontent.com/81825828/180602537-537ab119-a335-49e0-bf7e-bf015058feb4.png)

In this case, re-enter your answer given the limitations.

There are also some another constraints.
