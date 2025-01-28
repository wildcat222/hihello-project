ARG VERSION=7.14.1
FROM docker.elastic.co/elasticsearch/elasticsearch:${VERSION}

# 기존 플러그인 디렉토리가 있으면 제거
RUN if [ -d "/usr/share/elasticsearch/plugins/analysis-nori" ]; then \
      elasticsearch-plugin remove analysis-nori; \
    fi

# Nori 플러그인 설치
RUN elasticsearch-plugin install analysis-nori
