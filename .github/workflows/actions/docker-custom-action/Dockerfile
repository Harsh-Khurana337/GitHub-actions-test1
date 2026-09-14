FROM alpine:3.19
COPY entrypoint.sh ./entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
# this is to force github to do a rebuild and not use caching