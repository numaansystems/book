# Azure AD Authentication Configuration

This application supports authentication via Azure Active Directory (Azure AD) using OAuth2/OpenID Connect.

## Setup Instructions

### 1. Register Application in Azure AD

1. Go to [Azure Portal](https://portal.azure.com)
2. Navigate to **Azure Active Directory** > **App registrations**
3. Click **New registration**
4. Fill in the details:
   - **Name**: Budget Book Application
   - **Supported account types**: Select appropriate option (e.g., "Accounts in this organizational directory only")
   - **Redirect URI**: 
     - Platform: Web
     - URI: `http://localhost:8080/login/oauth2/code/azure` (for local development)
     - For production, use your production URL
5. Click **Register**

### 2. Configure Application

After registration, you'll see the application overview page:

1. Copy the **Application (client) ID** - this is your `AZURE_CLIENT_ID`
2. Copy the **Directory (tenant) ID** - this is your `AZURE_TENANT_ID`

### 3. Create Client Secret

1. In your app registration, go to **Certificates & secrets**
2. Click **New client secret**
3. Add a description and select expiration
4. Click **Add**
5. **Important**: Copy the secret value immediately - this is your `AZURE_CLIENT_SECRET`
   (You won't be able to see it again)

### 4. Configure API Permissions

1. Go to **API permissions**
2. Click **Add a permission**
3. Select **Microsoft Graph**
4. Select **Delegated permissions**
5. Add the following permissions:
   - `openid`
   - `profile`
   - `email`
   - `User.Read`
6. Click **Add permissions**
7. (Optional) Click **Grant admin consent** if you have admin rights

### 5. Set Environment Variables

For production deployment, set these environment variables:

```bash
export AZURE_CLIENT_ID=your-application-client-id
export AZURE_CLIENT_SECRET=your-client-secret-value
export AZURE_TENANT_ID=your-tenant-id
```

Or create an `application-prod.yml` file:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          azure:
            client-id: your-application-client-id
            client-secret: your-client-secret-value
        provider:
          azure:
            issuer-uri: https://login.microsoftonline.com/your-tenant-id/v2.0
```

### 6. Run Application

#### Local Development (No Authentication)

For local development without Azure AD:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

This will run the application without authentication requirements.

#### With Azure AD Authentication

```bash
# Set environment variables first
export AZURE_CLIENT_ID=your-client-id
export AZURE_CLIENT_SECRET=your-client-secret
export AZURE_TENANT_ID=your-tenant-id

# Run application
mvn spring-boot:run
```

Or with profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Application Behavior

### With Azure AD Enabled (Default Profile)
- Users will be redirected to Azure AD login page
- After successful authentication, they'll be redirected to `/snappies`
- User information is available from OAuth2 authenticated principal

### Local Development Mode (`local` profile)
- No authentication required
- All endpoints are accessible without login
- Useful for development and testing

## Security Configuration

The application uses Spring Security with OAuth2 Client support:

- **Protected Resources**: All application endpoints require authentication
- **Public Resources**: Static resources (`/css/**`, `/js/**`, `/vue/**`) are publicly accessible
- **Login**: Handled by Azure AD OAuth2 flow
- **Logout**: Available at `/logout` endpoint

## Troubleshooting

### Common Issues

1. **"Redirect URI mismatch"**
   - Ensure the redirect URI in Azure AD matches exactly: `http://localhost:8080/login/oauth2/code/azure`
   - Check for trailing slashes

2. **"Invalid client secret"**
   - Verify the client secret hasn't expired
   - Check that you copied the secret value, not the secret ID

3. **"Tenant not found"**
   - Verify the tenant ID is correct
   - Ensure you're using the tenant ID, not the tenant name

4. **"AADSTS50011: The reply URL specified in the request does not match"**
   - Add the correct redirect URI in Azure AD app registration
   - For production, add: `https://your-domain.com/login/oauth2/code/azure`

## Additional Resources

- [Azure AD OAuth2 Documentation](https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow)
- [Spring Security OAuth2 Client](https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html)
- [Microsoft Identity Platform](https://docs.microsoft.com/en-us/azure/active-directory/develop/)
