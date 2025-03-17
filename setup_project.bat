@echo off
echo Setting up project structure...

REM Create necessary directories for Java files
mkdir "app\src\main\java\net\zygotelabs\locker"
mkdir "app\src\main\java\net\zygotelabs\locker\utils"
mkdir "app\src\main\java\net\zygotelabs\locker\dialogs"

REM Copy source files to proper structure
xcopy "sources\net\zygotelabs\locker\MainActivity.java" "app\src\main\java\net\zygotelabs\locker\" /Y
xcopy "sources\net\zygotelabs\locker\MainFragment.java" "app\src\main\java\net\zygotelabs\locker\" /Y
xcopy "sources\net\zygotelabs\locker\DeviceAdmin.java" "app\src\main\java\net\zygotelabs\locker\" /Y

xcopy "sources\net\zygotelabs\locker\utils\DeviceAdminManager.java" "app\src\main\java\net\zygotelabs\locker\utils\" /Y
xcopy "sources\net\zygotelabs\locker\utils\NotificationHandler.java" "app\src\main\java\net\zygotelabs\locker\utils\" /Y

xcopy "sources\net\zygotelabs\locker\dialogs\EnableLockProtectionDialog.java" "app\src\main\java\net\zygotelabs\locker\dialogs\" /Y
xcopy "sources\net\zygotelabs\locker\dialogs\DisableLockProtectionDialog.java" "app\src\main\java\net\zygotelabs\locker\dialogs\" /Y

REM Remove conflicting BuildConfig.java from sources
if exist "sources\net\zygotelabs\locker\BuildConfig.java" (
    echo Removing conflicting BuildConfig.java
    del "sources\net\zygotelabs\locker\BuildConfig.java"
)

echo Project setup completed successfully!
