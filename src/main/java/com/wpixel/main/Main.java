package com.wpixel.main;

import com.wpixel.server.Server;

public class Main {
	public static void main(String[] args) {
		new Server().run(8080);
	}
}
