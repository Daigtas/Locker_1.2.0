@echo off
mkdir -p "app\src\main\java\net\zygotelabs\locker\utils"
mkdir -p "app\src\main\java\net\zygotelabs\locker\dialogs"

xcopy "sources\net\zygotelabs\locker\MainActivity.java" "app\src\main\java\net\zygotelabs\locker\" /Y
xcopy "sources\net\zygotelabs\locker\MainFragment.java" "app\src\main\java\net\zygotelabs\locker\" /Y
xcopy "sources\net\zygotelabs\locker\DeviceAdmin.java" "app\src\main\java\net\zygotelabs\locker\" /Y

xcopy "sources\net\zygotelabs\locker\utils\*.java" "app\src\main\java\net\zygotelabs\locker\utils\" /Y
xcopy "sources\net\zygotelabs\locker\dialogs\*.java" "app\src\main\java\net\zygotelabs\locker\dialogs\" /Y

echo Source files copied successfully!
