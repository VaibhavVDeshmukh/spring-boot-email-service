# Spring Boot Email Service

This is a standalone mailing service application built with Java Spring Boot. It handles everything related to sending emails, including:

- Sending simple emails
- Sending HTML emails
- Sending emails with attachments
- Sending emails with inline images
- Scheduling emails
- Creating and managing email templates

## Features

1. **Simple Email**: Send plain text emails to multiple recipients.
2. **HTML Email**: Send rich text emails with HTML content.
3. **Attachments**: Send emails with one or more attachments.
4. **Inline Images**: Embed images directly into the email content.
5. **Scheduled Emails**: Schedule emails to be sent at a later time.
6. **Email Templates**: Create, update, delete, and retrieve email templates dynamically.

## Technology Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Thymeleaf**
- **H2 Database (for testing)**
- **Maven** 

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/spring-boot-email-service.git
    cd spring-boot-email-service
    ```

2. Build the project:
    ```bash
    mvn clean install
    ```

3. Run the application:
    ```bash
    mvn spring-boot:run
    ```

### API Endpoints

- **Send Simple Email**: 
  - `POST /api/mail/send-simple`
  - Request parameters: `to`, `subject`, `text`

- **Send HTML Email**: 
  - `POST /api/mail/send-html`
  - Request body: `to`, `subject`, `htmlContent`

- **Send Email with Attachments**: 
  - `POST /api/mail/send-attachment`
  - Request body: `to`, `subject`, `text`, `attachments`

- **Schedule Email**:
  - `POST /api/mail/schedule`
  - Request body: `to`, `subject`, `text`, `scheduleTime`

- **Create Email Template**: 
  - `POST /api/templates/create`
  - Request body: `name`, `subject`, `content`

- **Get All Templates**:
  - `GET /api/templates/all`

### Testing

You can test the API endpoints using Postman or any other API testing tool. Ensure that you replace the placeholders in the example requests with actual values.

### Contributing

Feel free to submit issues or pull requests. Contributions are welcome!

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

