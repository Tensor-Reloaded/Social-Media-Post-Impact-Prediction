from src.logic import SimpleConsoleOrchestrator, NoneValueConsoleOrchestrator
import datetime as dt

if __name__ == '__main__':
    process = SimpleConsoleOrchestrator(
        q="travel OR travelling",
        lang="en",
        until=dt.datetime(2021, 10, 8, 0, 0, 0)
    )
    process.main()
