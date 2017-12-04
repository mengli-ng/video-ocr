up:
	docker stack deploy -c docker-compose/docker-compose.yml video-ocr

down:
	docker stack rm video-ocr
