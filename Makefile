.PHONY: test

repl:
	clj -M:dev -m "tools.repl" -p 3001

test:
	clj -M:dev -m "tools.test"
