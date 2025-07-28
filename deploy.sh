#!/bin/bash

# Configuration
PI_HOST="192.168.1.109"
PI_USER="admin"
PI_HOME="/home/admin"
PROJECT_DIR="composeApp"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}[$(date '+%H:%M:%S')] 🔐 Testing SSH connection...${NC}"
if ! ssh -o ConnectTimeout=5 -o BatchMode=yes ${PI_USER}@${PI_HOST} exit 2>/dev/null; then
    echo -e "${RED}[$(date '+%H:%M:%S')] ❌ SSH connection failed!${NC}"
    echo -e "${YELLOW}[$(date '+%H:%M:%S')] 💡 Run: ssh-copy-id ${PI_USER}@${PI_HOST}${NC}"
    exit 1
fi

echo -e "${GREEN}[$(date '+%H:%M:%S')] ✅ SSH connection OK${NC}"
echo -e "${BLUE}[$(date '+%H:%M:%S')] 🚀 Starting deployment to Raspberry Pi...${NC}"
JAR_FILE="./build/compose/jars/com.worldline.quiz-macos-arm64-1.0.0-release.jar"

if [ ! -f "$JAR_FILE" ]; then
    echo -e "${RED}[$(date '+%H:%M:%S')] ❌ Jar file not found: $JAR_FILE${NC}"
    exit 1
fi

echo -e "${GREEN}[$(date '+%H:%M:%S')] ✅ Found jar: $(basename $JAR_FILE)${NC}"

# Step 2: Prepare the Pi
echo -e "${BLUE}[$(date '+%H:%M:%S')] 🔧 Preparing Raspberry Pi...${NC}"
ssh ${PI_USER}@${PI_HOST} << 'EOF'
    echo "🗑️  Removing X11 config..."
    sudo rm -f /usr/share/X11/xorg.conf.d/20-noglamor.conf

    echo "🛑 Stopping running app instances..."
    pkill -f "com.worldline.quiz" || true

    echo "📁 Creating app directory..."
    mkdir -p ~/app

    echo "🧹 Cleaning old jar files..."
    rm -f ~/app/*.jar
EOF

if [ $? -ne 0 ]; then
    echo -e "${RED}[$(date '+%H:%M:%S')] ❌ Failed to prepare Raspberry Pi!${NC}"
    exit 1
fi

echo -e "${GREEN}[$(date '+%H:%M:%S')] ✅ Pi preparation completed${NC}"

# Step 3: Copy the jar file
echo -e "${BLUE}[$(date '+%H:%M:%S')] 📁 Copying jar file...${NC}"
scp "$JAR_FILE" ${PI_USER}@${PI_HOST}:${PI_HOME}/app/

if [ $? -ne 0 ]; then
    echo -e "${RED}[$(date '+%H:%M:%S')] ❌ Failed to copy jar file!${NC}"
    exit 1
fi

echo -e "${GREEN}[$(date '+%H:%M:%S')] ✅ Jar file copied successfully${NC}"

# Step 4: Launch the application
echo -e "${BLUE}[$(date '+%H:%M:%S')] 🚀 Launching application...${NC}"
ssh ${PI_USER}@${PI_HOST} << EOF
    cd ~/app

    JAR_FILE=\$(ls *macos-arm64*-release.jar | head -1)
    echo "🚀 Starting application: \$JAR_FILE"

    export DISPLAY=:0.0
    export MESA_EXTENSION_OVERRIDE="-GL_ARB_invalidate_subdata"
    nohup java -cp "\$JAR_FILE" com.worldline.quiz.MainKt > app.log 2>&1 &

    echo "⏳ Waiting for application to start..."
    sleep 3

    if pgrep -f "com.worldline.quiz" > /dev/null; then
        echo "✅ Application started successfully!"
        echo "📝 Log file: ~/app/app.log"
        echo "🆔 PID: \$(pgrep -f com.worldline.quiz)"
    else
        echo "❌ Failed to start application. Last 10 lines of log:"
        tail -n 10 app.log
        exit 1
    fi
EOF

if [ $? -eq 0 ]; then
    echo -e "${GREEN}[$(date '+%H:%M:%S')] 🎉 Deployment completed successfully!${NC}"
else
    echo -e "${RED}[$(date '+%H:%M:%S')] ❌ Deployment failed!${NC}"
    exit 1
fi