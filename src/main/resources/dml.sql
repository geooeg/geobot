/**
 * Author:  ljzhang
 * Created: 27.12.2015
 */

-- V1
ALTER TABLE coordinates
  ADD latitude_conv NUMERIC;

ALTER TABLE coordinates
  ADD longitude_conv NUMERIC;

ALTER TABLE coordinates
  ADD altitude_conv NUMERIC;

-- V2
ALTER TABLE coordinates
  ADD gps_timestamp VARCHAR;

ALTER TABLE coordinates
  ADD gps_datestamp VARCHAR;

ALTER TABLE coordinates
  ADD gps_dop VARCHAR;

ALTER TABLE coordinates
  DROP timestamp;

ALTER TABLE coordinates
  ADD timestamp TIMESTAMP;
