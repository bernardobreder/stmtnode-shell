#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>

int main() {
	setbuf(stdout, 0);
	int server_fd = socket(AF_INET, SOCK_STREAM, 0);
	if (!server_fd >= 0) {
		printf("Could create a socket\n");
		return 1;
	}
	int port = 8080;
	struct sockaddr_in server_ds;
	memset(&server_ds, 0, sizeof(struct sockaddr_in));
	server_ds.sin_family = AF_INET;
	server_ds.sin_addr.s_addr = htons(INADDR_ANY);
	server_ds.sin_port = htons(port);
	int opt_val = 1;
	setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &opt_val, sizeof(int));
	{
		int err = bind(server_fd, ((struct sockaddr*)&server_ds), sizeof(struct sockaddr_in));
		if (err < 0) {
			printf("Could not bind socket\n");
			close(server_fd);
			return 1;
		}
	}
	{
		int err = listen(server_fd, 128);
		if (err < 0) {
			printf("Could not listen on socket\n");
			close(server_fd);
			return 1;
		}
	}
	printf("Server started at localhost:%d\n", port);
	char buffer = 8 * 1024;
	while (1) {
		struct sockaddr_in client;
		socklen_t client_len = sizeof(client);
		int client_fd = accept(server_fd, ((struct sockaddr*)&client), &client_len);
		if (!client_fd >= 0) {
			continue;
		}
		ssize_t read = recv(client_fd, buffer, sizeof(buffer), 0);
		if (!read > 0) {
			close(client_fd);
			continue;
		}
		{
			int err = send(client_fd, buffer, read, 0);
			if (err <= 0) {
				close(client_fd);
				continue;
			}
		}
		close(client_fd);
	}
	close(server_fd);
	return 0;
	close(server_fd);
}
