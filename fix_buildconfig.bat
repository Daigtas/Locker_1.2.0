@echo off
echo Fixing BuildConfig issue...

REM Create the source directory structure if it doesn't exist
mkdir "app\src\main\java\net\zygotelabs\locker" 2>nul
mkdir "app\src\main\java\net\zygotelabs\locker\utils" 2>nul
mkdir "app\src\main\java\net\zygotelabs\locker\dialogs" 2>nul

REM Delete old BuildConfig.java files that conflict with auto-generated one
IF EXIST "sources\net\zygotelabs\locker\BuildConfig.java" (
    echo Removing conflicting BuildConfig.java from sources folder
    del /F "sources\net\zygotelabs\locker\BuildConfig.java"
)

IF EXIST "app\src\main\java\net\zygotelabs\locker\BuildConfig.java" (
    echo Removing conflicting BuildConfig.java from app src folder
    del /F "app\src\main\java\net\zygotelabs\locker\BuildConfig.java"
)

REM Delete old R.java files that conflict with auto-generated one
IF EXIST "sources\net\zygotelabs\locker\R.java" (
    echo Removing conflicting R.java from sources folder
    del /F "sources\net\zygotelabs\locker\R.java"
)

IF EXIST "app\src\main\java\net\zygotelabs\locker\R.java" (
    echo Removing conflicting R.java from app src folder
    del /F "app\src\main\java\net\zygotelabs\locker\R.java"
)

REM Copy Java files to correct locations
echo Copying MainActivity.java...
xcopy "sources\net\zygotelabs\locker\MainActivity.java" "app\src\main\java\net\zygotelabs\locker\" /Y

echo Copying MainFragment.java...
xcopy "sources\net\zygotelabs\locker\MainFragment.java" "app\src\main\java\net\zygotelabs\locker\" /Y

echo Copying DeviceAdmin.java...
xcopy "sources\net\zygotelabs\locker\DeviceAdmin.java" "app\src\main\java\net\zygotelabs\locker\" /Y

echo Copying utility classes...
xcopy "sources\net\zygotelabs\locker\utils\*.java" "app\src\main\java\net\zygotelabs\locker\utils\" /Y

echo Copying dialog classes...
xcopy "sources\net\zygotelabs\locker\dialogs\*.java" "app\src\main\java\net\zygotelabs\locker\dialogs\" /Y

echo All files copied and conflicts resolved.
echo Please run "gradlew clean build" to build your project.
