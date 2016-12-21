* TEst


docker build -t johanlarsson/viaplay-demo-app .

docker run -p 49160:8080 -d johanlarsson/viaplay-demo-app

docker exec -it <container id> /bin/bash
