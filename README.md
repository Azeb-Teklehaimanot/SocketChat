# WhatsApp SocketChat Server


## Overview
This project is a simple chat application that demonstrates real-time communication using WebSockets.
A simplified front end is provided to facilitate testing and to demonstrate the application's capabilities and to easily test every functionality.

## Front End http://localhost:8080
## http://localhost:8080/swagger-ui/index.html

## Testing Process

### User Registration and Sign-In
1. **Navigate to the Application:** Open your web browser and navigate to the application URL.
2. **Register a User:** Use the 'User Registration' section to create a new user by entering a username and password.
3. **Sign-In:** Use the 'User Sign-In' section to log in using the username and password of the registered user.

### Chat Room Interaction
1. **Create a Chat Room:** After signing in, use the 'Create Chat Room' section to create a new chat room by specifying its name and maximum size.
2. **Join a Chat Room:** Use the 'Join/Leave Chat Room' dropdown to select a chat room and click 'Join'.
3. **Send Messages:** Once inside a chat room, use the 'Chat Messages' section to type and send messages.

### File Attachment
1. **Attach a File:** Use the file input in the 'Chat Messages' section to select and attach files. Video size 10MB is not supported while the is no maximum size limit
2. **Send a Message with Attachment:** After attaching a file, type a message and click 'Send'.

### Notes
- Evaluate different scenarios like multiple users, various file types for attachment, etc.
- Verify the behavior when a user joins, leaves, and sends messages in a chat room.
- If a user is joined to a room then messages that are sent by another user will be visible on the text area of the ui.
