CREATE OR REPLACE FUNCTION create_at()
RETURNS trigger AS $BODY$
begin
	NEW.created_at = now();
	RETURN new;
end;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE OR REPLACE FUNCTION update_at()
RETURNS trigger AS $BODY$
begin
	NEW.updated_at = now();
	RETURN NEW;
end;
$BODY$
LANGUAGE plpgsql VOLATILE;

create or replace trigger trigger_create_at
	before insert on usuario
for each row
execute procedure create_at();

create or replace trigger trigger_update_at
	before update on usuario
for each row
execute procedure update_at();