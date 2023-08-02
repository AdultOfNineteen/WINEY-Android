# 나만의 와인을 찾는 여행 Winey의 안드로이드 레포지토리입니다.

## 구조 설명

기본적으로 멀티모듈을 활용해 앱을 구성하며 [일반적인 모듈화 패턴](https://developer.android.com/topic/modularization/patterns?hl=ko)과 [Now in Android](https://github.com/android/nowinandroid)를 참고하여 구성했습니다.

![WINEY 의존성 그래프 drawio (1)](https://github.com/DongChyeon/WINEY-Android/assets/64844115/fec531d7-3d0f-41a2-b5f8-735fa7202754)

## 모듈 설명

- app: 앱 수준과 전체 코드베이스를 바인딩하는 계층으로, MainActivity, WineyApp 등 포함. feature 모듈의 navigation을 통합하여 관리

- domain: data와 feature의 중간 계층으로 model, mapper, usecase 포함
- data: 직접 데이터를 받아오는 계층으로 api, model, datasource, repository 포함
- feature: 각 기능 화면을 직접 표시하는 계층으로 uiState, viewmodel, screen, 그리고 해당 기능에 대한 navigation 포함
- core: 다른 모듈에서 자주 사용하는 코드가 포함

각 모듈에 대한 DI, 리소스는 해당 모듈에서 관리합니다.

## 깃 컨벤션

|접두사|설명|
|------|---|
|Feat|새 기능 (new feature)|
|Fix|버그 수정|
|Docs|문서 수정|
|Style|코드 스타일 관련 수정 (포맷팅 등 실제 코드 변화는 아닌 경우)|
|Refactor|리팩토링 작업|
|Test|테스트 코드의 추가나 테스트 코드에 대한 리팩토링 작업 (메인 코드는 변경 X)|
|Chore|빌드와 관련된 것들을 업데이트하는 경우|
|Design|UI 변경 작업|
|Comment|주석의 작성이나 변경|
|Rename|파일, 폴더, 패키지 등의 이름만 수정하거나 옮기기만 한 경우|
|Remove|파일 삭제만 한 경우|

### 개발 환경

* [버전 정보](./buildSrc/src/main/java/Versions.kt)
* [사용 라이브러리](./buildSrc/src/main/java/Dependency.kt)

### 빌드 조건

* 그래들 버전 8.0.0 이상
* JDK 17
