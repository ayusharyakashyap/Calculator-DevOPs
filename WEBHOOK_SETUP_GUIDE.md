# GitHub Webhook Setup Guide

This guide will help you set up GitHub webhooks to automatically trigger your Jenkins pipeline when you push code to your repository.

## Prerequisites

- [x] Jenkins running on localhost:8080
- [x] Enhanced Jenkinsfile with webhook triggers
- [x] GitHub repository (Calculator-DevOPs)
- [x] Complete DevOps pipeline working

## Option 1: Using ngrok (Recommended for Development)

### Step 1: Install ngrok
```bash
# Install ngrok using Homebrew
brew install ngrok/ngrok/ngrok

# Or download from https://ngrok.com/download
```

### Step 2: Create ngrok Account and Get Auth Token
1. Go to https://ngrok.com/signup
2. Sign up for a free account
3. Get your auth token from https://dashboard.ngrok.com/get-started/your-authtoken
4. Configure ngrok:
```bash
ngrok authtoken YOUR_AUTH_TOKEN_HERE
```

### Step 3: Expose Jenkins to Internet
```bash
# Run this command to expose Jenkins
ngrok http 8080
```

You'll see output like:
```
Session Status                online
Account                       your-email@example.com
Version                       3.x.x
Region                        United States (us)
Latency                       45ms
Web Interface                 http://127.0.0.1:4040
Forwarding                    https://abc123.ngrok.io -> http://localhost:8080
```

**Keep this terminal open!** Copy the `https://abc123.ngrok.io` URL.

### Step 4: Configure GitHub Webhook

1. Go to your GitHub repository: https://github.com/ayusharyakashyap/Calculator-DevOPs
2. Click **Settings** tab
3. Click **Webhooks** in the left sidebar
4. Click **Add webhook**
5. Fill in the details:
   - **Payload URL**: `https://abc123.ngrok.io/github-webhook/`
   - **Content type**: `application/json`
   - **Which events**: Select "Just the push event"
   - **Active**: [Checked]
6. Click **Add webhook**

### Step 5: Test the Webhook

1. Make a small change to any file in your repository
2. Commit and push:
```bash
git add .
git commit -m "Test webhook trigger"
git push origin main
```
3. Check Jenkins at http://localhost:8080 - you should see a new build automatically started!

## Option 2: Using GitHub Actions (Alternative)

If ngrok doesn't work, you can set up GitHub Actions to trigger Jenkins:

### Create `.github/workflows/trigger-jenkins.yml`:
```yaml
name: Trigger Jenkins Pipeline

on:
  push:
    branches: [ main ]

jobs:
  trigger-jenkins:
    runs-on: ubuntu-latest
    steps:
    - name: Trigger Jenkins Build
      run: |
        curl -X POST "http://YOUR_JENKINS_URL/job/scientific-calculator-pipeline/build" \
          --user "admin:YOUR_JENKINS_TOKEN"
```

## Webhook Flow Diagram

```
GitHub Push → Webhook → ngrok → Jenkins → Pipeline Stages
     ↓
1. Pull GitHub Repo
2. Build with Maven  
3. Run Test Cases
4. Package Application
5. Build Docker Image
6. Test Docker Image
7. Login to Docker Hub
8. Push to Docker Hub
9. Deploy on Local System
10. Health Check
11. Email Notification
```

## Email Configuration

To enable email notifications, configure Jenkins:

1. Go to **Manage Jenkins** → **Configure System**
2. Find **Extended E-mail Notification** section
3. Configure SMTP settings:
   - **SMTP server**: smtp.gmail.com (for Gmail)
   - **SMTP port**: 587
   - **Use SMTP Authentication**: [checked]
   - **Username**: your-email@gmail.com
   - **Password**: your-app-password
4. Update the email address in Jenkinsfile:
   ```groovy
   to: "your-actual-email@gmail.com"
   ```

## Troubleshooting

### Common Issues:

1. **Webhook not triggering**:
   - Check ngrok is running and URL is correct
   - Verify webhook URL ends with `/github-webhook/`
   - Check Jenkins logs for webhook events

2. **Jenkins build fails**:
   - Check console output in Jenkins
   - Verify all tools paths are correct
   - Ensure Docker Desktop is running

3. **Email not sending**:
   - Configure SMTP settings in Jenkins
   - Use app-specific password for Gmail
   - Check Jenkins system logs

### Useful Commands:
```bash
# Check ngrok status
curl http://127.0.0.1:4040/api/tunnels

# Test webhook manually
curl -X POST https://your-ngrok-url.ngrok.io/github-webhook/ \
  -H "Content-Type: application/json" \
  -d '{"ref":"refs/heads/main"}'

# Check Jenkins logs
tail -f ~/.jenkins/logs/jenkins.log
```

## Security Notes

- ngrok free tier has limitations (2 hours session, random URLs)
- For production, use proper reverse proxy or cloud hosting
- Never expose Jenkins directly to internet without authentication
- Use webhook secrets for additional security

## Next Steps

1. Set up ngrok and expose Jenkins
2. Configure GitHub webhook
3. Test with a sample push
4. Configure email notifications
5. Document the complete automated flow

Once this is working, your complete DevOps pipeline will be fully automated!

---

**Created**: $(date)
**Author**: DevOps Pipeline Setup
**Status**: Ready for implementation
