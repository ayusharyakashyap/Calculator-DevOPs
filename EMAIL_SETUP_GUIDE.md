# Jenkins Email Configuration Guide

Complete guide to set up email notifications in Jenkins for your DevOps pipeline.

## Prerequisites 

- [x] Jenkins running on localhost:8080
- [x] Gmail account: ayush06022004@gmail.com
- [x] Updated Jenkinsfile with your email address

## Step 1: Generate Gmail App Password 

Since Gmail uses 2-factor authentication, you need an App Password:

### 1.1 Enable 2-Factor Authentication
1. Go to https://myaccount.google.com/security
2. Enable **2-Step Verification** if not already enabled
3. Complete the setup process

### 1.2 Generate App Password
1. Go to https://myaccount.google.com/apppasswords
2. Select **Mail** as the app
3. Select **Other (Custom name)** as device
4. Enter: `Jenkins CI/CD Pipeline`
5. Click **Generate**
6. **SAVE THIS PASSWORD** - you'll need it for Jenkins
   ```
   Example: abcd efgh ijkl mnop
   ```

## Step 2: Install Email Extension Plugin

### 2.1 Install Plugin
1. Go to Jenkins: http://localhost:8080
2. Click **Manage Jenkins** → **Plugins**
3. Go to **Available plugins** tab
4. Search for: `Email Extension Plugin`
5. Select and click **Install**
6. Restart Jenkins if prompted

### 2.2 Verify Installation
- Go to **Manage Jenkins** → **System Configuration**
- You should see **Extended E-mail Notification** section

## Step 3: Configure SMTP Settings

### 3.1 System Configuration
1. Go to **Manage Jenkins** → **Configure System**
2. Scroll down to **Extended E-mail Notification** section

### 3.2 SMTP Server Settings
Configure these exact settings:

```
SMTP server: smtp.gmail.com
SMTP port: 587
Advanced → Use SMTP Authentication: [checked]
User Name: ayush06022004@gmail.com
Password: [Your 16-character app password]
Use SSL: [checked]
SMTP port: 587
```

### 3.3 Default Recipients
```
Default Recipients: ayush06022004@gmail.com
Default Subject: Jenkins Build: $PROJECT_NAME - Build #$BUILD_NUMBER - $BUILD_STATUS
```

### 3.4 Test Configuration
1. Scroll down to **Test configuration by sending test e-mail**
2. Enter: `ayush06022004@gmail.com`
3. Click **Test configuration**
4. Check your email for test message

## Step 4: Configure Global Email Settings

### 4.1 Jenkins Location
1. In **System Configuration** → **Jenkins Location**
2. Set **System Admin e-mail address**: `ayush06022004@gmail.com`

### 4.2 E-mail Notification (Basic)
1. Scroll to **E-mail Notification** section
2. Configure:
   ```
   SMTP server: smtp.gmail.com
   Advanced → Use SMTP Authentication: [checked]
   User Name: ayush06022004@gmail.com
   Password: [Your app password]
   Use SSL: [checked]
   SMTP Port: 587
   ```

## Step 5: Test Email Configuration

### 5.1 Manual Test
1. Create a simple Jenkins job
2. Add **Post-build Actions** → **E-mail Notification**
3. Enter your email and run the job
4. Check if you receive notification

### 5.2 Pipeline Test
Your updated Jenkinsfile already includes email notifications. When you push code:
1. Success emails will be sent with build details
2. Failure emails will include error information and console links

## Step 6: Troubleshooting

### Common Issues:

#### 6.1 Authentication Failed
```
Error: Authentication failed
Solution: 
- Verify 2FA is enabled
- Generate new App Password
- Use the 16-character password (not your Gmail password)
```

#### 6.2 Connection Timeout
```
Error: Could not connect to SMTP host
Solution:
- Verify SMTP settings: smtp.gmail.com:587
- Check SSL is enabled
- Test from different network
```

#### 6.3 Emails Not Received
```
Solution:
- Check Gmail spam folder
- Verify email address is correct
- Test with different email provider
```

### 6.4 Debug Steps
1. Check Jenkins logs:
   ```bash
   tail -f ~/.jenkins/logs/jenkins.log
   ```

2. Test SMTP from command line:
   ```bash
   telnet smtp.gmail.com 587
   ```

## Step 7: Email Templates

Your pipeline will send these types of emails:

### Success Email Features:
- HTML formatted with green theme
- Complete build information
- Git commit details (hash, author, message)
- Direct links to Docker Hub and Jenkins console
- Detailed list of completed tasks

### Failure Email Features:
- HTML formatted with red theme
- Error details and failed stage information
- Direct link to console output for debugging
- Troubleshooting steps
- Commit information that triggered the failure

## Step 8: Security Best Practices

### 8.1 App Password Security
- Store app password securely
- Don't share in code repositories
- Rotate passwords periodically

### 8.2 Jenkins Credentials
- Consider using Jenkins Credential Store
- Set up credential binding for sensitive data

## Step 9: Advanced Configuration

### 9.1 Multiple Recipients
Update Jenkinsfile for multiple emails:
```groovy
to: "ayush06022004@gmail.com,teammate@example.com"
```

### 9.2 Conditional Emails
Send emails only on specific conditions:
```groovy
// Only send on main branch
when {
    branch 'main'
}
```

### 9.3 Rich Content
Your emails already include:
- Commit information
- Build links
- Task completion status
- Docker Hub links

## Expected Email Content

### Success Email Sample:
```
Subject: Jenkins Build SUCCESS: Scientific-Calculator-Pipeline - Build #25

Content:
- Build information with links
- Latest commit details
- Complete task checklist
- Docker Hub link
- Health check results
```

### Failure Email Sample:
```
Subject: Jenkins Build FAILED: Scientific-Calculator-Pipeline - Build #25

Content:
- Error details and failed stage
- Console output link
- Commit that caused failure
- Troubleshooting steps
```

## Step 10: Verify Complete Setup

After configuration:

1. **Push code to GitHub**
2. **Jenkins pipeline triggers automatically**
3. **Email sent on success/failure**
4. **Check email for proper formatting**

## Quick Setup Commands

```bash
# Test SMTP connection
curl -v telnet://smtp.gmail.com:587

# Check Jenkins process
ps aux | grep jenkins

# View Jenkins logs
tail -f ~/.jenkins/logs/jenkins.log
```

---

**Your email setup is now complete!**

Push any code change to GitHub and you'll receive detailed email notifications about your DevOps pipeline status.

**Next Steps:**
1. Configure Gmail App Password
2. Set up SMTP in Jenkins
3. Test with a code push
4. Enjoy automatic email notifications!

**Created**: September 29, 2025
**Email**: ayush06022004@gmail.com
**Status**: Ready for configuration