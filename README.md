<div align="center">

<img src="https://github.com/Medi-Notes/.github/raw/main/profile/medinote_logo.png" width="400" alt="Medinote"/>

---
<details>
  <summary><strong>&nbsp;Table of Contents</strong></summary>

&nbsp;  
[About Project](#about-project)<br/>
[Team Members](#team-members)<br/>
[Built With](#built-with)<br/>
<!--
[Usage](#usage)<br/>
[Contact](#contact)<br/>
-->
</details>

</div>

<!-- ABOUT PROJECT -->
## About Project
Project Duration: February 05, 2024 - August 31, 2024

Medinote는 의대생들을 위한 STT 서비스입니다. 강의를 녹화한 파일을 업로드하게 되면, 텍스트로 변환되며 의학 용어는 영어로 변환되어 제공됩니다. 이를 수정하거나 문장 단위로 복사할 수 있는 기능을 제공하여 의대생들의 필기를 돕습니다.

### Key Feature
- **User Management:** Spring Security와 OAuth2 기반의 소셜 로그인을 지원합니다.
- **Audio File Upload:** 사용자는 사진을 Amazon S3에 업로드하여 메디노트를 생성할 수 있습니다.
- **Transform to Medinote:** Openai의 Whisper, Gpt를 이용하여 음성파일을 메디노트로 변환합니다.
- **One Click Copy:** 변환된 메디노트를 문장 별로 복사하여 손쉽게 이용할 수 있습니다.

<!-- Team -->
## Team Members
* **Team Member:** 성균관대학교 컴퓨터교육학과 정지윤 [@Jiyun](https://github.com/j2yun)
* **Team Member:** 세종대학교 컴퓨터공학과 나영채 [@YongChae](https://github.com/lunaB)
* **Team Member:** 영남대학교 의예과 양태현

&nbsp;

## Architecture
![alt text](https://github.com/Medi-Notes/.github/raw/main/profile/archi.png)

<!-- Built with -->
## Built With

![Java](https://img.shields.io/badge/Java-17.0.9-007496?style=for-the-badge)  
![Spring_boot](https://img.shields.io/badge/Springboot-3.2.7-6DB33F?style=for-the-badge&logo=springboot&logoColor=6DB33F)  
![EC2](https://img.shields.io/badge/ec2-deploy-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)

![React](https://img.shields.io/badge/React-18.2.5-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)  
![NodeJS](https://img.shields.io/badge/NodeJS-18.19.1-339933?style=for-the-badge&logo=nodedotjs&logoColor=yellow)  
![Vercel](https://img.shields.io/badge/vercel-deploy-000000?style=for-the-badge&logo=vercel&logoColor=white)

<!-- ![Python](https://img.shields.io/badge/Python-3.11.4-3776AB?style=for-the-badge&logo=python&logoColor=yellow)  
![React.js](https://img.shields.io/badge/React-18.2.0-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)  
![Docker](https://img.shields.io/badge/Docker-24.0.2-2496ED?style=for-the-badge&logo=docker&logoColor=white)  
![Yarn](https://img.shields.io/badge/Yarn-3.6.1-2C8EBB?style=for-the-badge&logo=yarn&logoColor=white)  
![Node.js](https://img.shields.io/badge/NodeJS-18.17.0-339933?style=for-the-badge&logo=nodedotjs&logoColor=yellow)  
![TypeScript](https://img.shields.io/badge/TypeScript-CDK-3178C6?style=for-the-badge&logo=typescript&logoColor=white)  
![Vercel](https://img.shields.io/badge/vercel-deploy-000000?style=for-the-badge&logo=vercel&logoColor=white)   -->