# 🏃 **Exercise App**

## 📋 **프로젝트 개요 (Project Overview)**
- **프로젝트명**: **Exercise App**
- **주제**: 운동 및 식단 기록 관리 애플리케이션
- **설명**:  
  이 애플리케이션은 사용자가 운동과 식단을 체계적으로 기록하고, 그 결과를 시각적으로 확인할 수 있도록 설계된 모바일 애플리케이션입니다.  
  운동을 통해 체중, 체지방, 근육량 등의 변화를 기록하며, 사용자가 설정한 운동 계획에 따라 세부적인 운동 루틴과 식단을 관리할 수 있습니다.  
  사용자는 운동 리스트를 직접 추가하거나, 운동 후 체중 변화 등을 그래프로 확인할 수 있습니다.  
  본 프로젝트는 **안드로이드 스튜디오**를 사용하여 개발되었으며, **Java**와 **XML**을 사용하여 화면을 구성하였습니다.
  영상은 google_Drive에 있습니다.

## 🛠️ **주요 기능 (Main Features)**
1. **🏋️ 운동 관리**
   - 사용자가 운동 루틴을 설정하고, 각 운동별 무게와 세트 수, 쉬는 시간 등을 기록할 수 있습니다.
   - 운동 목록을 선택하여 새로운 운동을 추가하거나, 이미 추가된 운동을 수정 및 삭제할 수 있습니다.

2. **📊 기록 확인**
   - 체중, 체지방, 근육량 등의 변화를 그래프로 확인할 수 있습니다.
   - 일자별 기록을 저장하여 시간 경과에 따른 변화 추이를 시각적으로 확인 가능합니다.

3. **🍽️ 식단 관리**
   - 사용자는 자신이 먹은 음식을 기록하고, 식단 리스트를 추가할 수 있습니다.
   - 각 식단별 칼로리와 영양소 정보를 입력하여, 하루의 총 섭취량을 관리할 수 있습니다.

4. **🔄 화면 전환 및 하단바 탐색**
   - 하단바를 통해 운동, 홈, 식단, 기록 등 주요 기능으로 쉽게 전환할 수 있습니다.
   - 각 화면에서는 뒤로가기 버튼을 눌러 이전 화면으로 돌아갈 수 있습니다.

## 📂 **폴더 구조 (Folder Structure)**
```
exercise_app/ ├── ppt 사진/ # 프로젝트 발표에 사용된 사진 폴더 ├── res/ # 리소스 파일 (이미지, XML 등) ├── AndroidManifest.xml # 안드로이드 메니페스트 파일 ├── Exercise.java # 운동 기능을 관리하는 클래스 ├── Food.java # 식단 기능을 관리하는 클래스 ├── Fragment_First.java # 첫 번째 프래그먼트 화면 ├── Fragment_Second.java # 두 번째 프래그먼트 화면 ├── Fragment_Third.java # 세 번째 프래그먼트 화면 ├── Fragment_Four.java # 네 번째 프래그먼트 화면 ├── Fragment_Five.java # 다섯 번째 프래그먼트 화면 ├── Global.java # 전역 변수 및 상수를 관리하는 클래스 ├── MainActivity.java # 메인 액티비티 ├── OnBackPressedListener.java # 뒤로가기 버튼 동작을 정의한 리스너 클래스 ├── SecondActivity.java # 두 번째 액티비티 화면 ├── termproject_ppt_real.pptx # 프로젝트 발표 자료 ├── 운동흐름_2단구조.jpg # 운동 흐름을 설명한 구조도 이미지 ├── 텀프로젝트 제안서.pdf # 프로젝트 제안서 파일 └── README.md # 프로젝트 설명 파일
```

## 💻 **기술 스택 및 개발 환경 (Tech Stack & Environment)**
- **프로그래밍 언어**: Java
- **개발 도구**: Android Studio
- **사용 기술**: Fragment, Intent, Handler
- **사용 API**: Android SDK
- **환경**: Android OS 9.0+

## 🔧 **기능 설명 (Feature Description)**

### 1. **앱 구조 및 주요 화면**
- **초기 로딩 화면**:
  - 애플리케이션을 실행하면 로고 화면이 1초간 표시된 후, 메인 화면으로 자동 이동합니다.

- **메인 화면**:
  - 하단에 **운동, 홈, 식단, 기록** 등의 네비게이션 바가 표시됩니다.
  - 각 버튼을 누르면, 해당하는 기능 화면으로 전환됩니다.

### 2. **운동 관리**
- 사용자는 원하는 운동을 선택하고, 무게, 세트 수, 쉬는 시간을 설정할 수 있습니다.
- 운동 리스트를 저장하고, 운동을 시작하면 각각의 설정에 맞추어 운동을 기록할 수 있습니다.

### 3. **기록 확인**
- 사용자가 입력한 체중, 근육량, 체지방 등의 데이터를 그래프로 확인할 수 있습니다.
- 날짜별로 기록을 추가하고, 기록된 데이터의 변화를 시각적으로 볼 수 있습니다.

### 4. **식단 관리**
- 사용자는 자신이 섭취한 음식의 이름과 칼로리, 영양 정보를 입력하여 하루의 식단을 관리할 수 있습니다.
- 식단 목록을 추가, 삭제, 수정할 수 있으며, 기록된 데이터를 기반으로 총 섭취 칼로리를 계산합니다.



