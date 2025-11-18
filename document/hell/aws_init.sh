#!/bin/bash
# 시스템 업데이트 및 Nginx 설치
sudo dnf update -y
sudo dnf install -y nginx

# Nginx 시작 및 부팅 시 자동 시작 설정
sudo systemctl start nginx
sudo systemctl enable nginx

# 기본 웹 페이지 생성
echo "<h1>Welcome to My Fedora Nginx Server</h1>" > /usr/share/nginx/html/index.html

# 시스템 업데이트 및 Nginx 설치
sudo dnf update -y
sudo dnf install -y nginx

# Nginx 시작 및 부팅 시 자동 시작 설정
sudo systemctl start nginx
sudo systemctl enable nginx

# 기본 웹 페이지 생성
echo "<h1>Welcome to My Fedora Nginx Server</h1>" > /usr/share/nginx/html/index.html

sudo dnf update -y
sudo dnf install -y nodejs npm

# 애플리케이션 생성
mkdir /home/ec2-user/myapp
cat <<EOF > /home/ec2-user/myapp/app.js
const http = require('http');
const hostname = '0.0.0.0';
const port = 3000;

const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end('Hello, World! This is Node.js running on Fedora EC2.\n');
});

server.listen(port, hostname, () => {
  console.log(\`Server running at http://\${hostname}:\${port}/\`);
});
EOF

# 애플리케이션 실행
cd /home/ec2-user/myapp
node app.js &
sudo dnf update -y
sudo dnf install -y aws-cli

# 다운로드한 파일 내용 출력
cat /home/ec2-user/my-file.txt
