.PHONY: grpc
grpc:
	for f in `find src/main/java/fun/witt/api/proto -name "*.proto" -type f`; do echo $$f; protoc -I=src/main/java/fun/witt/api/proto --proto_path=src --java_out=src/main/java $$f; done
